package util;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Description:Redis������
 * @author  liu
 * @version V1.0
 */


public class RedisUtil {
	private static Logger logger = Logger.getLogger(RedisUtil.class);
	private volatile static JedisPool JEDIS_POOL = null;

	private RedisUtil() {
	}

	

	/**
	 * Description:����ģʽ��ȡRedis���ӳ�
	 * @author  liu
	 * @parameter 
	 * @return 
	 */
	public static JedisPool getJedisPool() {
		if (JEDIS_POOL == null) {
			synchronized (RedisUtil.class) {
				if (JEDIS_POOL == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					// ������������, Ĭ��8�� ����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����
					config.setMaxIdle(PropertiesRedisUtil.getInt("REDIS_MAX_IDLE"));
					// ���������, Ĭ��8��
					config.setMaxTotal(PropertiesRedisUtil.getInt("REDIS_MAX_TOTAL"));
					// ��ʾ��borrow(����)һ��jedisʵ��ʱ�����ĵȴ�ʱ�䣬��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��
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
	
	
	/**
	 * �ͷ�jedis��Դ
	 * 
	 * @param jedis
	 */
	public static void close(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	
	

}
