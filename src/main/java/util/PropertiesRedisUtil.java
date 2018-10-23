package util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * @author  liu
 * @version V1.0
 */

public final class PropertiesRedisUtil {
	private static final String BUNDLE_NAME = "config/system";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesRedisUtil.class);

	private PropertiesRedisUtil() {
	}

	/**
	 * 根据key获取值，key不存在则返回null
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			LOGGER.error("getString", e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		return Integer.parseInt(RESOURCE_BUNDLE.getString(key));
	}
	
	
	public static void main(String[] args) {
		String value = getString("REDIS_ADDR");
		LOGGER.info("redis的IP地址=[{}]",value);
	}


}
