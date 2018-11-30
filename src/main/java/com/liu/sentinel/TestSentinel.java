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
		
		logger.info("Redis哨兵get方法:"+result);
		
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
					// 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
					config.setMaxIdle(PropertiesRedisUtil.getInt("REDIS_MAX_IDLE"));
					// 最大连接数, 默认8个
					config.setMaxTotal(PropertiesRedisUtil.getInt("REDIS_MAX_TOTAL"));
					// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
					config.setMaxWaitMillis(PropertiesRedisUtil.getInt("REDIS_WAIT_TIMEOUT"));
					//设置 Set<String> sentinels = new HashSet<String>();
				    Set<String> sentinels = new HashSet<>(Arrays.asList(
				    		   "68.168.138.63:26379",
				    		   "68.168.138.63:26380",
				    		   "68.168.138.63:26381"
				        ));
					 //masterName
					 String masterName = "mymaster";//可以通过配置文件获取
					JEDIS_POOL = new JedisSentinelPool(masterName,sentinels,config,20000,PropertiesRedisUtil.getString("REDIS_AUTH"));
				}
			}
		}
		return JEDIS_POOL;
	}
	
	
	
	/**
	 * 获取Jedis实例
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
