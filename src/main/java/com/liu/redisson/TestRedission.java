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
			// redisson获取分布式锁
			RLock lock = redisson.getLock("mylock");
			boolean tryLock = lock.tryLock();
			log.info("redisson分布式事务锁尝试开启结果------------" + tryLock);
			if (tryLock) {
				log.info("我获取了锁，开始工作。。。。。。。。。。。。。");
			}

		} catch (Exception e) {
			log.error("获取分布式锁出现错误" + e.getMessage());
		} finally {
			RLock lock = redisson.getLock("mylock");
			lock.unlock();
			log.info("redisson--------------分布式事务锁解锁");
            redisson.shutdown();
		}
	}
}
