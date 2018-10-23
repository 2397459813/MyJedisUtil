package service;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Description:Jedis
 * @author  liu
 * @version V1.0
 */


public interface ICacheService {
	public  boolean isExist(String paramString)throws Exception;
	public  boolean isExist(byte[] paramArrayOfByte)throws Exception;
	public  boolean setString(String paramString1, String paramString2)throws Exception;
	public  String getString(String paramString)throws Exception;
	public  Long del(String paramString)throws Exception;
	public  Long del(byte[] paramArrayOfByte)throws Exception;
	public  Long del(byte[]... paramVarArgs)throws Exception;
	public  Long del(String... paramVarArgs)throws Exception;
	
	/**
	 * Description:返回key的剩余生存时间
	 * @author  liu
	 * @parameter 
	 * @return 
	 */
	public  Long ttl(String paramString) throws Exception;
	public  Long expire(String paramString, int paramInt)throws Exception;
	
	public  boolean setObject(String paramString, Object paramObject)throws Exception;
	public  Object getObject(String paramString) throws Exception;
	public  boolean setex(String paramString1, String paramString2, int paramInt)throws Exception;
	public  boolean setex(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)throws Exception;
	public  boolean setexObject(String paramString, Object paramObject, int paramInt)throws Exception;
	
	 public  Long incr(String paramString)throws Exception;
	 public  Long incrBy(String paramString, long paramLong)throws Exception;
	 public  Long llen(byte[] paramArrayOfByte)throws Exception;
	 public  Long lpush(byte[] paramArrayOfByte, byte[]... paramVarArgs)throws Exception;
	 public  Long lpush(String paramString, String... paramVarArgs)throws Exception;
	 public  Long rpush(String paramString, String... paramVarArgs)throws Exception;
	 public  List<String> lrange(String paramString, long paramLong1, long paramLong2)throws Exception;
	 public  List<byte[]> lrange(byte[] paramArrayOfByte, long paramLong1, long paramLong2)throws Exception;
	 public  String lindex(String paramString, long paramLong)throws Exception;
	 public  String lpop(String paramString)throws Exception;
	 public  Long lrem(String paramString1, long paramLong, String paramString2)throws Exception;
	 public  String putObjectMap(String paramString, Map<String, Object> paramMap)throws Exception;
	 public  String putMap(String paramString, Map<String, String> paramMap)throws Exception;
	public  String putMap(byte[] paramArrayOfByte, Map<byte[], byte[]> paramMap)throws Exception;
	
	public  Map<String, String> getMap(String paramString)throws Exception;
	public  Map<byte[], byte[]> getMap(byte[] paramArrayOfByte)throws Exception;
	public  Long hset(String paramString1, String paramString2, String paramString3)throws Exception;
	public  Long hset(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)throws Exception;
	public  Long hsetObject(String paramString1, String paramString2, Object paramObject)throws Exception;
	
	 public  String hget(String paramString1, String paramString2)throws Exception;
	 public  byte[] hget(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)throws Exception;
	public  Object hgetObject(String paramString1, String paramString2)throws Exception;
	public  Long hlen(String paramString)throws Exception;
	public  Boolean hexists(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)throws Exception;
	public  Set<String> hkeys(String paramString)throws Exception;
	public  List<String> hvals(String paramString)throws Exception;
	public  Long hdel(byte[] paramArrayOfByte, byte[]... paramVarArgs)throws Exception;
	public  Long sadd(String paramString, String... paramVarArgs)throws Exception;
	public  Long scard(String paramString)throws Exception;
	public  Set<String> smembers(String paramString)throws Exception;
	
	public  Long zadd(String paramString1, double paramDouble, String paramString2)throws Exception;
	public  Set<String> zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2)throws Exception;
	public  Long zremrangeByScore(String paramString, double paramDouble1, double paramDouble2)throws Exception;
	
	public  String rpop(String paramString)throws Exception;
	 public  Double zscore(String paramString1, String paramString2)throws Exception;
	 public  Set<String> zrevrange(String paramString, long paramLong1, long paramLong2)throws Exception;
	
	 public  Set<String> zrange(String paramString, long paramLong1, long paramLong2)throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
