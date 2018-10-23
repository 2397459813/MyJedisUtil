package service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;
import service.ICacheService;
import util.RedisUtil;


/**
 * Description:Jedis缓存服务常用方法工具类
 * @author  liu
 * @version V1.0
 */



public class CacheServiceImpl implements ICacheService{
	
	private  static final Logger LOGGER= LoggerFactory.getLogger(CacheServiceImpl.class);

	@Override
	public boolean isExist(String key) throws Exception {
		boolean flag = false;
		if(key.equals("")||key==null){
			return false;
		}
		flag = RedisUtil.getJedis().exists(key.getBytes()).booleanValue();
		return flag;
	}

	@Override
	public boolean isExist(byte[] key) throws Exception {
		boolean flag = false;
		if(key.equals("")||key==null){
			return false;
		}
		flag = RedisUtil.getJedis().exists(key).booleanValue();
		return flag;
	}

	@Override
	public boolean setString(String key, String value)
			throws Exception {
		boolean flag = false;
		if(key.equals("")||key==null){
			return false;
		}
		String result = RedisUtil.getJedis().set(key, value);
		if(result.contains("OK")){
			flag = true;
		}
		return flag;
	}

	@Override
	public String getString(String key) throws Exception {
		String value = null;
		if(key.equals("")||key==null){
			return value;
		}
		String result = RedisUtil.getJedis().get(key);
		if(result==null||result.length()==0){
			return value;
		}
		
		return result;
		
	}

	@Override
	public Long del(String key) throws Exception {
	    long result = 0L;
	    if (StringUtils.isEmpty(key)) {
	      return Long.valueOf(result);
	    }
	    result = RedisUtil.getJedis().del(key.getBytes()).longValue();
	    
	    return Long.valueOf(result);
	}

	@Override
	public Long del(byte[] key) throws Exception {
		Long result= 0L;
		if(StringUtils.isEmpty(key)){
			return Long.valueOf(result);
		}
		result = RedisUtil.getJedis().del(key).longValue();
		return Long.valueOf(result);
	}

	@Override
	public Long del(byte[]... keys) throws Exception {
		Long result = 0L;
		result = RedisUtil.getJedis().del(keys);
		return Long.valueOf(result);
	}

	@Override
	public Long del(String... keys) throws Exception {
		
		Long result = 0L;
		result = RedisUtil.getJedis().del(keys);
		return Long.valueOf(result);
	}

	
	@Override
	public Long ttl(String key) throws Exception {
		  long result = 0L;
		    if (StringUtils.isEmpty(key)) {
		      return Long.valueOf(result);
		    }
		    result = RedisUtil.getJedis().ttl(key.getBytes()).longValue();
		    
		    return Long.valueOf(result);
	}

	@Override
	public Long expire(String key, int seconds) throws Exception {
		  long result = 0L;
		    if (StringUtils.isEmpty(key)) {
		      return Long.valueOf(result);
		    }
		    if (seconds >= 0) {
		      result = RedisUtil.getJedis().expire(key.getBytes(), seconds).longValue();
		    } else {
		      return Long.valueOf(result);
		    }
		    return Long.valueOf(result);
	}

	@Override
	public boolean setObject(String key, Object value){
			 boolean flag = false;
		    if ((StringUtils.isEmpty(key)) || (null == value)) {
		      return flag;
		    }
		    byte[] setkey = key.getBytes();
		    byte[] setvalue = SerializationUtils.serialize(value);
		    String result = RedisUtil.getJedis().set(setkey, setvalue);
		    if ("OK".equals(result)) {
		      flag = true;
		    }
		    return flag;
	}

	@Override
	public Object getObject(String key) throws Exception {
	    Object value = null;
	    if (StringUtils.isEmpty(key)) {
	      return value;
	    }
	    byte[] result = RedisUtil.getJedis().get(key.getBytes());
	    if ((result == null) || (result.length == 0)) {
	      return value;
	    }
	    value = SerializationUtils.deserialize(result);
	    
	    return value;
	}

	@Override
	public boolean setex(String key, String value, int seconds){
		    boolean flag = false;
		    if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(value))) {
		      return flag;
		    }
		    if (seconds >= 0)
		    {
		      byte[] setkey = key.getBytes();
		      byte[] setvalue = value.getBytes();
		      String result = RedisUtil.getJedis().setex(setkey, seconds, setvalue);
		      if ("OK".equals(result)) {
		        flag = true;
		      }
		    }
		    return flag;
	}

	@Override
	public boolean setex(byte[] key, byte[] value,
			int seconds) throws Exception {
		 boolean flag = false;
		    if ((key == null) || (key.length == 0) || (value == null) || (value.length == 0)) {
		      return flag;
		    }
		    if (seconds >= 0)
		    {
		      String result = RedisUtil.getJedis().setex(key, seconds, value);
		      if ("OK".equals(result)) {
		        flag = true;
		      }
		    }
		    return flag;
	}

	@Override
	public boolean setexObject(String key, Object value,
			int seconds) throws Exception {
		   boolean flag = false;
		    if ((StringUtils.isEmpty(key)) || (null == value)) {
		      return flag;
		    }
		    if (seconds >= 0)
		    {
		      byte[] setkey = key.getBytes();
		      byte[] setvalue = SerializationUtils.serialize(value);
		      String result = RedisUtil.getJedis().setex(setkey, seconds, setvalue);
		      if ("OK".equals(result)) {
		        flag = true;
		      }
		    }
		    return flag;
	}

	@Override
	public Long incr(String key) throws Exception {
		 long result = 0L;
		    if (StringUtils.isEmpty(key)) {
		      return Long.valueOf(result);
		    }
		    result = RedisUtil.getJedis().incr(key.getBytes()).longValue();
		    
		    return Long.valueOf(result);
	}

	@Override
	public Long incrBy(String key, long integer) throws Exception {
		long result = 0L;
	    if (StringUtils.isEmpty(key)) {
	      return Long.valueOf(result);
	    }
	    result =  RedisUtil.getJedis().incrBy(key.getBytes(), integer).longValue();
	    
	    return Long.valueOf(result);
	}

	@Override
	public Long llen(byte[] key) throws Exception {
		Long result = 0L;
		  if (StringUtils.isEmpty(key)) {
		      return Long.valueOf(result);
		    }
		  result = RedisUtil.getJedis().llen(key).longValue();
		return Long.valueOf(result);
	}

	@Override
	public Long lpush(byte[] key, byte[]... values)
			throws Exception {
		long result = 0L;
	    if ((key == null) || (key.length == 0)) {
		      return Long.valueOf(result);
		    }
		    result = RedisUtil.getJedis().lpush(key, values).longValue();
		    
		    return Long.valueOf(result);
	}

	@Override
	public Long lpush(String key, String... values)
			throws Exception {
		 long result = 0L;
		    if (StringUtils.isEmpty(key)) {
		      return Long.valueOf(result);
		    }
		    result = RedisUtil.getJedis().lpush(key, values).longValue();
		    
		    return Long.valueOf(result);
	}

	@Override
	public Long rpush(String key, String... values)
			throws Exception {
	    long result = 0L;
	    if (StringUtils.isEmpty(key)) {
	      return Long.valueOf(result);
	    }
	    result = RedisUtil.getJedis().rpush(key, values).longValue();
	    
	    return Long.valueOf(result);
	}

	@Override
	public List<String> lrange(String key, long start,
			long end) throws Exception {
		  List<String> value = null;
		    if (StringUtils.isEmpty(key)) {
		      return value;
		    }
		    value = RedisUtil.getJedis().lrange(key, start, end);
		    
		    return value;
	}

	@Override
	public List<byte[]> lrange(byte[] key, long start,
			long end) throws Exception {
		 List<byte[]> value = null;
		    if ((key == null) || (key.length == 0)) {
		      return value;
		    }
		    value = RedisUtil.getJedis().lrange(key, start, end);
		    
		    return value;
	}

	@Override
	public String lindex(String key, long index) throws Exception {
		String value = null;
	    if (StringUtils.isEmpty(key)) {
	      return value;
	    }
	    value = RedisUtil.getJedis().lindex(key, index);
	    
	    return value;
	}

	@Override
	public String lpop(String key) throws Exception {
		String value = null;
	    if (StringUtils.isEmpty(key)) {
	      return value;
	    }
	    value = RedisUtil.getJedis().lpop(key);
	    
	    return value;
	}

	@Override
	public Long lrem(String key, long count, String value)
			throws Exception {
		long result = 0L;
	    if (StringUtils.isEmpty(key)) {
	      return Long.valueOf(result);
	    }
	    result = RedisUtil.getJedis().lrem(key, count, value);
		return Long.valueOf(result);
	}

	@Override
	public String putObjectMap(String key, Map<String, Object> map)
			throws Exception {
	    String result = null;
	    if ((StringUtils.isEmpty(key)) || (map == null) || (map.size() < 1)) {
	      return result;
	    }
	    Set<Map.Entry<String, Object>> entrySet = map.entrySet();
	    Map<String,String> hashMap = new HashMap<String,String>();
	    for(Map.Entry<String, Object> entry : entrySet) {
	    	Object value = entry.getValue();
	    	String hashValue = null;
	    	if(value == null) {
	    		hashValue = "";
	    	}else if(value instanceof String) {
	    		hashValue = (String)value;
	    	}else if(value instanceof Integer || value instanceof Long 
	    			|| value instanceof Double || value instanceof Float) {
	    		hashValue = value.toString();
	    	}else if(value instanceof BigDecimal) {
	    		hashValue = ((BigDecimal) value).toPlainString();
	    	}
	    	hashMap.put(entry.getKey(), hashValue);
	    }
	    result = RedisUtil.getJedis().hmset(key, hashMap);
	    
	    return result;
	}

	@Override
	public String putMap(String key, Map<String, String> map)
			throws Exception {
		  String result = null;
		    if ((StringUtils.isEmpty(key)) || (map == null) || (map.size() < 1)) {
		      return result;
		    }
		    result = RedisUtil.getJedis().hmset(key, map);
		    
		    return result;
	}

	@Override
	public String putMap(byte[] key, Map<byte[], byte[]> map)
			throws Exception {
		 String result = null;
		    if ((key == null) || (key.length == 0) || (map == null) || (map.size() < 1)) {
		      return result;
		    }
		    result = RedisUtil.getJedis().hmset(key, map);
		    
		    return result;
	}

	@Override
	public Map<String, String> getMap(String key) throws Exception {
		 Map<String, String> value = null;
		    if (StringUtils.isEmpty(key)) {
		      return value;
		    }
		    value = RedisUtil.getJedis().hgetAll(key);
		    
		    return value;
	}

	@Override
	public Map<byte[], byte[]> getMap(byte[] key) throws Exception {
		Map<byte[], byte[]> value = null;
	    if ((key == null) || (key.length == 0)) {
	      return value;
	    }
	    value = RedisUtil.getJedis().hgetAll(key);
	    
	    return value;
	}

	@Override
	public Long hset(String key, String field,
			String value) throws Exception {
	    long result = 0L;
	    if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(field)) || (StringUtils.isEmpty(value))) {
	      return Long.valueOf(result);
	    }
	    result = RedisUtil.getJedis().hset(key, field, value).longValue();
	    
	    return Long.valueOf(result);
	}

	@Override
	public Long hset(byte[] key, byte[] field,
			byte[] value) throws Exception {
		 long result = 0L;
		    if ((key == null) || (key.length == 0) || (field == null) || (field.length == 0) || (value == null) || (value.length == 0)) {
		      return Long.valueOf(result);
		    }
		    result = RedisUtil.getJedis().hset(key, field, value).longValue();
		    
		    return Long.valueOf(result);
	}

	@Override
	public Long hsetObject(String key, String field,
			Object value) throws Exception {
		 long result = 0L;
		    if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(field)) || (null == value)) {
		      return Long.valueOf(result);
		    }
		    byte[] setKey = key.getBytes();
		    byte[] setField = field.getBytes();
		    byte[] setValue = SerializationUtils.serialize(value);
		    result = RedisUtil.getJedis().hset(setKey, setField, setValue).longValue();
		    
		    return Long.valueOf(result);
	}

	@Override
	public String hget(String key, String field)
			throws Exception {
		 String value = null;
		    if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(field))) {
		      return value;
		    }
		    value = RedisUtil.getJedis().hget(key, field);
		    
		    return value;
	}

	@Override
	public byte[] hget(byte[] key, byte[] field)
			throws Exception {
		 byte[] value = null;
		    if ((key == null) || (key.length == 0) || (field == null) || (field.length == 0)) {
		      return value;
		    }
		    value = RedisUtil.getJedis().hget(key, field);
		    
		    return value;
	}

	@Override
	public Object hgetObject(String key, String field)
			throws Exception {
		 Object value = null;
		    if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(field))) {
		      return value;
		    }
		    byte[] result = RedisUtil.getJedis().hget(key.getBytes(), field.getBytes());
		    if ((result == null) || (result.length == 0)) {
		      return value;
		    }
		    value = SerializationUtils.deserialize(result);
		    
		    return value;
	}

	@Override
	public Long hlen(String key) throws Exception {
		 long result = 0L;
		    if (StringUtils.isEmpty(key)) {
		      return Long.valueOf(result);
		    }
		    result = RedisUtil.getJedis().hlen(key).longValue();
		    
		    return Long.valueOf(result);
	}

	@Override
	public Boolean hexists(byte[] key, byte[] field)
			throws Exception {
		boolean flag = false;
	    if ((key == null) || (key.length == 0) || (field == null) || (field.length == 0)) {
	      return Boolean.valueOf(flag);
	    }
	    flag = RedisUtil.getJedis().hexists(key, field).booleanValue();
	    
	    return Boolean.valueOf(flag);
	  
	}


	@Override
	public List<String> hvals(String key) throws Exception {
		 List<String> values = null;
		    if (StringUtils.isEmpty(key)) {
		      return values;
		    }
		    values = RedisUtil.getJedis().hvals(key);
		    
		    return values;
	}

	@Override
	public Set<String> hkeys(String key) throws Exception {
		Set<String> keys = null;
	    if (StringUtils.isEmpty(key)) {
	      return keys;
	    }
	    keys = RedisUtil.getJedis().hkeys(key);
	    
	    return keys;
	}

	@Override
	public Long hdel(byte[] key, byte[]... field)
			throws Exception {
		 long result = 0L;
		    if ((key == null) || (key.length == 0)) {
		      return Long.valueOf(result);
		    }
		    return RedisUtil.getJedis().hdel(key, field);
	}

	@Override
	public Long sadd(String key, String... members)
			throws Exception {
		  long result = 0L;
		    if (StringUtils.isEmpty(key)) {
		      return Long.valueOf(result);
		    }
		    result = RedisUtil.getJedis().sadd(key, members).longValue();
		    
		    return Long.valueOf(result);
	}

	@Override
	public Long scard(String key) throws Exception {
		long result = 0L;
	    if (StringUtils.isEmpty(key)) {
	      return Long.valueOf(result);
	    }
	    result =  RedisUtil.getJedis().scard(key).longValue();
	    
	    return Long.valueOf(result);
	}

	@Override
	public Set<String> smembers(String key) throws Exception {
		  Set<String> value = null;
		    if (StringUtils.isEmpty(key)) {
		      return value;
		    }
		    value = RedisUtil.getJedis().smembers(key);
		    
		    return value;
	}

	@Override
	public Long zadd(String key, double score,
			String member) throws Exception {
		long result = 0L;
	    if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(member))) {
	      return Long.valueOf(result);
	    }
	    result = RedisUtil.getJedis().zadd(key.getBytes(), score, member.getBytes()).longValue();
	    
	    return Long.valueOf(result);
	}

	@Override
	public Set<String> zrevrangeByScore(String key,
			double max, double min) throws Exception {
		Set<String> value = null;
	    if (StringUtils.isEmpty(key)) {
	      return value;
	    }
	    if (max > min) {
	      value = RedisUtil.getJedis().zrevrangeByScore(key, max, min);
	    } else {
	      return value;
	    }
	    return value;
	}

	@Override
	public Long zremrangeByScore(String key, double min,
			double max) throws Exception {
		long result = 0L;
	    if (StringUtils.isEmpty(key)) {
	      return Long.valueOf(result);
	    }
	    result = RedisUtil.getJedis().zremrangeByScore(key, min, max).longValue();
	    
	    return Long.valueOf(result);
	}

	@Override
	public String rpop(String key) throws Exception {
		 String value = null;
		    if (StringUtils.isEmpty(key)) {
		      return value;
		    }
		    //移除并返回列表的尾元素
		    byte[] result = RedisUtil.getJedis().rpop(key.getBytes());
		    if ((result == null) || (result.length == 0)) {
		      return value;
		    }
		    value = new String(result);
		    
		    return value;
	
	}

	@Override
	public Double zscore(String key, String member)
			throws Exception {
		 Double result = null;
		    if (StringUtils.isEmpty(key)) {
		      return result;
		    }
		    result = RedisUtil.getJedis().zscore(key, member);
		    
		    return result;
	}

	@Override
	public Set<String> zrevrange(String key, long start,
			long end) throws Exception {
		 Set<String> value = null;
		    if (StringUtils.isEmpty(key)) {
		      return value;
		    }
		    value = RedisUtil.getJedis().zrevrange(key, start, end);
		    
		    return value;
	}

	@Override
	public Set<String> zrange(String key, long start,
			long end) throws Exception {
		 Set<String> value = null;
		    if (StringUtils.isEmpty(key)) {
		      return value;
		    }
		    value = RedisUtil.getJedis().zrange(key, start, end);
		    
		    return value;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
