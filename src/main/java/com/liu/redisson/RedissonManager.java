package com.liu.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.PropertiesRedisUtil;

public class RedissonManager {
	private static final Logger log = LoggerFactory.getLogger(RedissonManager.class);
	private static RedissonClient redisson = null;
	private static String nodes[] = {
			"redis://68.168.138.63:6382",
			"redis://68.168.138.63:6383",
			"redis://68.168.138.63:6384",
			"redis://68.168.138.63:6385",
			"redis://68.168.138.63:6386",
			"redis://68.168.138.63:6387",
			"redis://68.168.138.63:6388",
			"redis://68.168.138.63:6389"};
	public static RedissonClient getRedisson() throws Exception {
		if (redisson == null) {
			synchronized (RedissonManager.class) {
				if (redisson == null) {
					Config config = new Config();
					String pass = PropertiesRedisUtil.getString("REDIS_AUTH");
					config.useClusterServers().setConnectTimeout(5000)
					.setMasterConnectionMinimumIdleSize(5)
							.addNodeAddress(nodes)
							.setPassword(pass);
					redisson = Redisson.create(config);
				}
			}
		}
		return redisson;
	}

}
