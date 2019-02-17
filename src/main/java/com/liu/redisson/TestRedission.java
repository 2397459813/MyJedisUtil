package com.liu.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRedission {
	private static final Logger log = LoggerFactory.getLogger(TestRedission.class);

	public static void main(String[] args) throws Exception {
		RedissonClient redisson = RedissonManager.getRedisson();
		try {
			// redisson��ȡ�ֲ�ʽ��
			RLock lock = redisson.getLock("mylock");
			boolean tryLock = lock.tryLock();
			log.info("redisson�ֲ�ʽ���������Կ������------------" + tryLock);
			if (tryLock) {
				log.info("�һ�ȡ��������ʼ������������������������������");
			}

		} catch (Exception e) {
			log.error("��ȡ�ֲ�ʽ�����ִ���" + e.getMessage());
		} finally {
			RLock lock = redisson.getLock("mylock");
			lock.unlock();
			log.info("redisson--------------�ֲ�ʽ����������");
            redisson.shutdown();
		}
	}
}
