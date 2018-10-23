package com.liu.jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import util.RedisUtil;

public class TestRedisType {
	private static Logger logger = Logger.getLogger(TestRedisType.class);

	public static void main(String[] args) throws InterruptedException {
		Jedis jedis = RedisUtil.getJedis();
		TestRedisType.testString(jedis);
		// TestRedisType.testMap(jedis);
		// TestRedisType.testList(jedis);
		// TestRedisType.testSet(jedis);
		//testPipeLineAndNormal(jedis);
		RedisUtil.close(jedis);
	}

	/**
	 * �ַ�������
	 * 
	 * @param jedis
	 */

	public static void testString(Jedis jedis) {
		jedis.set("name", "hello jedis!");// ��key-->name�з�����value-->xinxin
		System.out.println(jedis.get("name"));// ִ�н����xinxin

		jedis.append("name", " is my lover"); // ƴ��
		System.out.println(jedis.get("name"));

		jedis.del("name"); // ɾ��ĳ����
		System.out.println(jedis.get("name"));
		// ���ö����ֵ��
		jedis.mset("name", "ĳĳĳ", "age", "24", "qq", "476777XXX");
		jedis.incr("age"); // ���м�1����
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
	}

	/**
	 * map �÷�
	 * 
	 * @param jedis
	 */
	public static void testMap(Jedis jedis) {
		// -----�������----------
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		jedis.hmset("user", map);
		// ȡ��user�е�name��ִ�н��:[minxr]-->ע������һ�����͵�List
		// ��һ�������Ǵ���redis��map�����key����������Ƿ���map�еĶ����key�������key���Ը�������ǿɱ����
		List<String> rsmap = jedis.hmget("user", "name", "age");
		System.out.println(rsmap);

		// ɾ��map�е�ĳ����ֵ
		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user", "age")); // ��Ϊɾ���ˣ����Է��ص���null
		System.out.println(jedis.hlen("user")); // ����keyΪuser�ļ��д�ŵ�ֵ�ĸ���2
		System.out.println(jedis.exists("user"));// �Ƿ����keyΪuser�ļ�¼ ����true
		System.out.println(jedis.hkeys("user"));// ����map�����е�����key
		System.out.println(jedis.hvals("user"));// ����map�����е�����value

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}
	}

	/**
	 * jedis����List
	 */
	public static void testList(Jedis jedis) {
		// ��ʼǰ�����Ƴ����е�����
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		// ����key java framework�д����������
		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");
		// ��ȡ����������jedis.lrange�ǰ���Χȡ����
		// ��һ����key���ڶ�������ʼλ�ã��������ǽ���λ�ã�jedis.llen��ȡ���� -1��ʾȡ������
		System.out.println(jedis.lrange("java framework", 0, -1));

		jedis.del("java framework");
		jedis.rpush("java framework", "spring");
		jedis.rpush("java framework", "struts");
		jedis.rpush("java framework", "hibernate");
		System.out.println(jedis.lrange("java framework", 0, -1));
	}

	/**
	 * jedis����Set
	 */
	public static void testSet(Jedis jedis) {
		// ���
		jedis.sadd("user1", "liuling");
		jedis.sadd("user1", "xinxin");
		jedis.sadd("user1", "ling");
		jedis.sadd("user1", "zhangxinxin");
		jedis.sadd("user1", "who");
		// �Ƴ�noname
		jedis.srem("user1", "who");
		System.out.println(jedis.smembers("user1"));// ��ȡ���м����value
		System.out.println(jedis.sismember("user1", "who"));// �ж� who
															// �Ƿ���user���ϵ�Ԫ��
		System.out.println(jedis.srandmember("user1"));
		System.out.println(jedis.scard("user1"));// ���ؼ��ϵ�Ԫ�ظ���
	}

	
	
	
	public static void testPipeLineAndNormal(Jedis jedis) throws InterruptedException {
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			jedis.set(String.valueOf(i), String.valueOf(i));
		}
		long end = System.currentTimeMillis();
		logger.info("the jedis total time is:" + (end - start));

		Pipeline pipe = jedis.pipelined();// �ȴ���һ��pipeline�����Ӷ���
		//jedis.pipelined
		long start_pipe = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			pipe.set(String.valueOf(i), String.valueOf(i));
		}
		pipe.sync();// ��ȡ���е�response
		long end_pipe = System.currentTimeMillis();
		logger.info("the pipe total time is:" + (end_pipe - start_pipe));

		BlockingQueue<String> logQueue = new LinkedBlockingQueue<String>();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			logQueue.put("i=" + i);
		}
		long stop = System.currentTimeMillis();
		logger.info("the BlockingQueue total time is:" + (stop - begin));
	}
	
	
	
	
	

}
