package util;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Description:Redis工具类
 * @author  liu
 * @version V1.0
 */


public class RedisUtil {
	private static Logger logger = Logger.getLogger(RedisUtil.class);
	private volatile static JedisPool JEDIS_POOL = null;

	private RedisUtil() {
	}

	

	/**
	 * Description:单利模式获取Redis连接池
	 * @author  liu
	 * @parameter 
	 * @return 
	 */
	public static JedisPool getJedisPool() {
		if (JEDIS_POOL == null) {
			synchronized (RedisUtil.class) {
				if (JEDIS_POOL == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					// 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
					config.setMaxIdle(PropertiesRedisUtil.getInt("REDIS_MAX_IDLE"));
					// 最大连接数, 默认8个
					config.setMaxTotal(PropertiesRedisUtil.getInt("REDIS_MAX_TOTAL"));
					// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
					config.setMaxWaitMillis(PropertiesRedisUtil.getInt("REDIS_WAIT_TIMEOUT"));
					JEDIS_POOL = new JedisPool(config, 
							PropertiesRedisUtil.getString("REDIS_ADDR"),
							PropertiesRedisUtil.getInt("REDIS_PORT"),
							PropertiesRedisUtil.getInt("REDIS_CONN_TIMEOUT"),
							PropertiesRedisUtil.getString("REDIS_AUTH"));
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
	
	
	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void close(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	
	

}
