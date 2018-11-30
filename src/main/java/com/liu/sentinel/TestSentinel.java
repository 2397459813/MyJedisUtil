package com.liu.sentinel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import util.PropertiesRedisUtil;
import util.RedisUtil;

public class TestSentinel {
	private static Logger logger = Logger.getLogger(RedisUtil.class);
	private volatile static JedisSentinelPool JEDIS_POOL = null;

	public static void main(String[] args) {
		
		Map<String, String> result = getJedis().hgetAll("myhash");
		
		logger.info("Redis�ڱ�get����:"+result);
		
	}
	
	
	private TestSentinel() {

	}

	/*
	 * public JedisSentinelPool(String masterName,
                         Set<String> sentinels,
                         org.apache.commons.pool2.impl.GenericObjectPoolConfig poolConfig,
                         String password)
	 */
	
	public static JedisSentinelPool getJedisPool() {
		if (JEDIS_POOL == null) {
			synchronized (RedisUtil.class) {
				if (JEDIS_POOL == null) {
					GenericObjectPoolConfig config = new GenericObjectPoolConfig();
					// ������������, Ĭ��8�� ����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����
					config.setMaxIdle(PropertiesRedisUtil.getInt("REDIS_MAX_IDLE"));
					// ���������, Ĭ��8��
					config.setMaxTotal(PropertiesRedisUtil.getInt("REDIS_MAX_TOTAL"));
					// ��ʾ��borrow(����)һ��jedisʵ��ʱ�����ĵȴ�ʱ�䣬��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��
					config.setMaxWaitMillis(PropertiesRedisUtil.getInt("REDIS_WAIT_TIMEOUT"));
					//���� Set<String> sentinels = new HashSet<String>();
				    Set<String> sentinels = new HashSet<>(Arrays.asList(
				    		   "68.168.138.63:26379",
				    		   "68.168.138.63:26380",
				    		   "68.168.138.63:26381"
				        ));
					 //masterName
					 String masterName = "mymaster";//����ͨ�������ļ���ȡ
					JEDIS_POOL = new JedisSentinelPool(masterName,sentinels,config,20000,PropertiesRedisUtil.getString("REDIS_AUTH"));
				}
			}
		}
		return JEDIS_POOL;
	}
	
	
	
	/**
	 * ��ȡJedisʵ��
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {

			Jedis resource = getJedisPool().getResource();
			return resource;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
