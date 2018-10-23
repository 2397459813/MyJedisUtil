package com.liu.jedis;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import util.RedisUtil;

public class TestPipline {
	private static Logger logger = Logger.getLogger(TestPipline.class);
	public static void main(String[] args) throws InterruptedException {
		Jedis jedis = RedisUtil.getJedis();
		testPipeLineAndNormal(jedis);

	}

	/*
	 * ������ͨģʽ��PipeLineģʽ��Ч�ʣ� ���Է�������redis�в���10000������
	 */

	public static void testPipeLineAndNormal(Jedis jedis) throws InterruptedException {
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			jedis.set(String.valueOf(i), String.valueOf(i));
		}
		long end = System.currentTimeMillis();
		logger.info("the jedis total time is:" + (end - start));

		Pipeline pipe = jedis.pipelined();// �ȴ���һ��pipeline�����Ӷ���
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
