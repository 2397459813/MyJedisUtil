package com.liu.clusterpipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.liu.cluster.TestCluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.JedisClusterCRC16;
import util.RedisUtil;

public class TestClusterPipeline {
	private static Logger logger = Logger.getLogger(TestClusterPipeline.class);
	private volatile static JedisCluster JEDIS_POOL; 

	public static JedisCluster getJedisPool() {
		if (JEDIS_POOL == null) {
			synchronized (TestCluster.class) {
				if (JEDIS_POOL == null) {
					 // ��Ӽ�Ⱥ�ķ���ڵ�Set����
			        Set<HostAndPort> hostAndPortsSet = new HashSet<HostAndPort>();
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6382));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6383));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6384));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6385));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6386));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6387));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6388));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6389));
			        // Jedis���ӳ�����
			        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			        // ������������, Ĭ��8��
			        jedisPoolConfig.setMaxIdle(10);
			        // ���������, Ĭ��8��
			        jedisPoolConfig.setMaxTotal(100);
			        //��С����������, Ĭ��0
			        jedisPoolConfig.setMinIdle(0);
			        // ��ȡ����ʱ�����ȴ�������(�������Ϊ����ʱBlockWhenExhausted),�����ʱ�����쳣, С����:������ȷ����ʱ��,  Ĭ��-1
			        jedisPoolConfig.setMaxWaitMillis(5000); // ����5��
			        //���õ���connection����validateObjectУ��
			        jedisPoolConfig.setTestOnBorrow(true);
			        JEDIS_POOL = new JedisCluster(hostAndPortsSet, 5000,5000,200,"123456a",jedisPoolConfig);
				}
			}
		}
		return JEDIS_POOL;
	}
	
	
	/**
	 * slot��Ӧnode
	 * @param anyHostAndPortStr
	 * @return
	 * {0=68.168.138.63:6388, 1364=68.168.138.63:6388, 1365=68.168.138.63:6382, 5460=68.168.138.63:6382,
	 *  5461=68.168.138.63:6388, 6826=68.168.138.63:6388, 6827=68.168.138.63:6383, 10922=68.168.138.63:6383, 
	 * 10923=68.168.138.63:6388, 12287=68.168.138.63:6388, 12288=68.168.138.63:6384, 16383=68.168.138.63:6384}
	 * 
	 */
	private static TreeMap<Long, String> getSlotHostMap(String anyHostAndPortStr) {
		TreeMap<Long, String> tree = new TreeMap<Long, String>();
		String parts[] = anyHostAndPortStr.split(":");
		HostAndPort anyHostAndPort = new HostAndPort(parts[0], Integer.parseInt(parts[1]));
		try {
			Jedis jedisNode = new Jedis(anyHostAndPort.getHost(), anyHostAndPort.getPort());
			jedisNode.auth("*************");//����У��
			List<Object> list = jedisNode.clusterSlots();
			for (Object object : list) {
				List<Object> list1 = (List<Object>) object;
				List<Object> master = (List<Object>) list1.get(2);
				String hostAndPort = new String((byte[]) master.get(0)) + ":" + master.get(1);
				tree.put((Long) list1.get(0), hostAndPort);
				tree.put((Long) list1.get(1), hostAndPort);
			}
			jedisNode.close();
		} catch (Exception e) {

		}
		return tree;
	}

	
	public static void main(String[] args) {
		
	    /*	String anyHostAndPortStr = "68.168.138.63:6384";
		TreeMap<Long, String> map = getSlotHostMap("68.168.138.63:6384");
		System.out.println(map);
		
		List<String> keys = new ArrayList<String>();
		keys.add("name");
		keys.add("username");
		keys.add("foo");
		Map<JedisPool, List<String>> map = getPoolKeyMap(keys);
		System.out.println(map);
		*/
		
		List<String> keys = new ArrayList<String>();
		keys.add("myset");
		keys.add("my");
		Map<String, Set<String>> map = batchGetSetData(keys);
		System.out.println(map);
	}
	
	
	/**
		 * ��key��slot��������
		 * @param keys
		 * @return
		 */
	private static Map<JedisPool, List<String>> getPoolKeyMap(List<String> keys) {
		Map<JedisPool, List<String>> poolKeysMap = new LinkedHashMap<JedisPool, List<String>>();
		try {
			for (String key : keys) {

				int slot = JedisClusterCRC16.getSlot(key);
				TreeMap<Long, String> slotHostMap = getSlotHostMap("68.168.138.63:6384");
				//��ȡ����Ӧ��Jedis���󣬴˴�+1����ٽ�����
				Map.Entry<Long, String> entry = slotHostMap.lowerEntry(Long.valueOf(slot+1));
				Map<String, JedisPool> nodeMap = getJedisPool().getClusterNodes();
				JedisPool jedisPool = nodeMap.get(entry.getValue());

				if (poolKeysMap.containsKey(jedisPool)) {
					poolKeysMap.get(jedisPool).add(key);
				} else {
					List<String> subKeyList = new ArrayList<String>();
					subKeyList.add(key);
					poolKeysMap.put(jedisPool, subKeyList);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return poolKeysMap;
	}


	/**
	 * ������ȡset��������
	 * @param keys
	 * @return
	 */
	public static Map<String, Set<String>> batchGetSetData(List<String> keys) {
		if (keys == null || keys.isEmpty()) {
			return null;
		}
		Map<JedisPool, List<String>> poolKeysMap = getPoolKeyMap(keys);
		Map<String, Set<String>> resultMap = new HashMap<String, Set<String>>();
		for (Map.Entry<JedisPool, List<String>> entry : poolKeysMap.entrySet()) {
			JedisPool jedisPool = entry.getKey();
			List<String> subkeys = entry.getValue();
			if (subkeys == null || subkeys.isEmpty()) {
				continue;
			}
			//����jedis����
			Jedis jedis = null;
			Pipeline pipeline = null;
			List<Object> subResultList = null;
			try {
				jedis = jedisPool.getResource();
				pipeline = jedis.pipelined();

				for (String key : subkeys) {
					pipeline.smembers(key);
				}

				subResultList = pipeline.syncAndReturnAll();
			} catch (JedisConnectionException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			} finally {
				if (pipeline != null)
					try {
						pipeline.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//�ͷ�jedis����
				if (jedis != null) {
					jedis.close();
				}
			}
			if (subResultList == null || subResultList.isEmpty()) {
				continue;
			}
			if (subResultList.size() == subkeys.size()) {
				for (int i = 0; i < subkeys.size(); i++) {
					String key = subkeys.get(i);
					Object result = subResultList.get(i);
					resultMap.put(key, (Set<String>) result);
				}
			} else {
				System.out.println("redis cluster pipeline error!");
			}
		}
		return resultMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
