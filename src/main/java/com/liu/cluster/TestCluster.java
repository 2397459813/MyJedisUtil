package com.liu.cluster;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;



public class TestCluster {
	private static Logger logger = Logger.getLogger(TestCluster.class);
	private volatile static JedisCluster JEDIS_POOL; 
	
	/*
	 * 
	 *  public JedisCluster(HostAndPort node, int connectionTimeout, int soTimeout,
                      int maxAttempts, String password, final GenericObjectPoolConfig poolConfig) {
    super(Collections.singleton(node), connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
        }
	 */
	
	public static JedisCluster getJedisPool() {
		if (JEDIS_POOL == null) {
			synchronized (TestCluster.class) {
				if (JEDIS_POOL == null) {
					 // 添加集群的服务节点Set集合
			        Set<HostAndPort> hostAndPortsSet = new HashSet<HostAndPort>();
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6382));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6383));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6384));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6385));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6386));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6387));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6388));
			        hostAndPortsSet.add(new HostAndPort("68.168.138.63", 6389));
			        // Jedis连接池配置
			        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			        // 最大空闲连接数, 默认8个
			        jedisPoolConfig.setMaxIdle(10);
			        // 最大连接数, 默认8个
			        jedisPoolConfig.setMaxTotal(100);
			        //最小空闲连接数, 默认0
			        jedisPoolConfig.setMinIdle(0);
			        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
			        jedisPoolConfig.setMaxWaitMillis(5000); // 设置5秒
			        //对拿到的connection进行validateObject校验
			        jedisPoolConfig.setTestOnBorrow(true);
			        JEDIS_POOL = new JedisCluster(hostAndPortsSet, 5000,5000,200,"**********",jedisPoolConfig);
				}
			}
		}
		return JEDIS_POOL;
	}
	

	
	  
	    public void testKey() throws InterruptedException {
	        //System.out.println("清空数据："+jedis.flushDB());
	        System.out.println("判断某个键是否存在："+getJedisPool().exists("username"));
	        System.out.println("新增<'username','wukong'>的键值对："+getJedisPool().set("username", "xiaohai"));
	        System.out.println("是否存在:"+getJedisPool().exists("username"));
	        System.out.println("新增<'password','password'>的键值对："+getJedisPool().set("password", "123456"));
	        //Set<String> keys = jedis.keys("*");
	        // System.out.println("系统中所有的键如下："+keys);
	        System.out.println("删除键password:"+getJedisPool().del("password"));
	        System.out.println("判断键password是否存在："+getJedisPool().exists("password"));
	        System.out.println("设置键username的过期时间为10s:"+getJedisPool().expire("username", 10));
	        TimeUnit.SECONDS.sleep(2); // 线程睡眠2秒System.out.println("查看键username的剩余生存时间："+jedis.ttl("username"));
	        System.out.println("查看键username的剩余生存时间："+getJedisPool().ttl("username"));
	        System.out.println("移除键username的生存时间："+getJedisPool().persist("username"));
	        System.out.println("查看键username的剩余生存时间："+getJedisPool().ttl("username"));
	        System.out.println("查看键username所存储的值的类型："+getJedisPool().type("username"));
	        System.out.println("查看key:username"+getJedisPool().get("username"));
	    }
	    
	 

	   
	
	    public void testString() throws InterruptedException {
		    JedisCluster jedis = getJedisPool();
	        //jedis.flushDB();
	        System.out.println("===========增加数据===========");
	        System.out.println(jedis.set("key1","value1"));
	        System.out.println(jedis.set("key2","value2"));
	        System.out.println(jedis.set("key3", "value3"));
	        System.out.println("删除键key2:"+jedis.del("key2"));
	        System.out.println("获取键key2:"+jedis.get("key2"));
	        System.out.println("修改key1:"+jedis.set("key1", "value1Changed"));
	        System.out.println("获取key1的值："+jedis.get("key1"));
	        System.out.println("在key3后面加入值："+jedis.append("key3", "End"));
	        System.out.println("key3的值："+jedis.get("key3"));
	        //命令的时候才会去连接连接，集群中连接是对一个节点连接，不能判断多个key经过crc16算法所对应的槽在一个节点上，不支持多key获取、删除
	        //System.out.println("增加多个键值对："+jedis.mset("key01","value01","key02","value02"));
	        //System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));
	        //System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03","key04"));
	        //System.out.println("删除多个键值对："+jedis.del(new String[]{"key01","key02"}));
	        //System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));

	        //jedis.flushDB();
	        System.out.println("===========新增键值对防止覆盖原先值==============");
	        System.out.println(jedis.setnx("key1", "value1"));
	        System.out.println(jedis.setnx("key2", "value2"));
	        System.out.println(jedis.setnx("key2", "value2-new"));
	        System.out.println(jedis.get("key1"));
	        System.out.println(jedis.get("key2"));

	        System.out.println("===========新增键值对并设置有效时间=============");
	        System.out.println(jedis.setex("key3", 2, "value3"));
	        System.out.println(jedis.get("key3"));
	        TimeUnit.SECONDS.sleep(3);
	        System.out.println(jedis.get("key3"));

	        System.out.println("===========获取原值，更新为新值==========");//GETSET is an atomic set this value and return the old value command.
	        System.out.println(jedis.getSet("key2", "key2GetSet"));
	        System.out.println(jedis.get("key2"));
	        System.out.println("获得key2的值的字串："+jedis.getrange("key2", 2, 4)); // 相当截取字符串的第二个位置-第四个位置的字符串
	    }
	   
	  
	    public void testNumber() {
	    	JedisCluster jedis = getJedisPool();
	        jedis.set("key1", "1");
	        jedis.set("key2", "2");
	        jedis.set("key3", "2.3");
	        System.out.println("key1的值："+jedis.get("key1"));
	        System.out.println("key2的值："+jedis.get("key2"));
	        System.out.println("key1的值加1："+jedis.incr("key1"));
	        System.out.println("获取key1的值："+jedis.get("key1"));
	        System.out.println("key2的值减1："+jedis.decr("key2"));
	        System.out.println("获取key2的值："+jedis.get("key2"));
	        System.out.println("将key1的值加上整数5："+jedis.incrBy("key1", 5));
	        System.out.println("获取key1的值："+jedis.get("key1"));
	        System.out.println("将key2的值减去整数5："+jedis.decrBy("key2", 5));
	        System.out.println("获取key2的值："+jedis.get("key2"));
	        System.out.println("key3的值："+jedis.get("key3"));
	        // 这里会报错，因为key3不是整数不能做计算:redis.clients.jedis.exceptions.JedisDataException: ERR value is not an integer or out of range
	        // System.out.println("key2的值减1："+jedis.decr("key3"));
	    }
	   
	    /***
	     * 列表
	     */
	  
	    public void testList() {
	    	JedisCluster jedis = getJedisPool();
	        System.out.println("===========添加一个list===========");
	        jedis.lpush("collections", "ArrayList", "Vector", "Stack", "HashMap", "WeakHashMap", "LinkedHashMap");
	        jedis.lpush("collections", "HashSet"); // 叠加
	        jedis.lpush("collections", "TreeSet"); // 叠加
	        jedis.lpush("collections", "TreeMap"); // 叠加
	        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));//-1代表倒数第一个元素，-2代表倒数第二个元素
	        System.out.println("collections区间0-3的元素："+jedis.lrange("collections",0,3)); // 前面4个值
	        System.out.println("===============================");
	        // 删除列表指定的值 ，第二个参数为删除的个数（有重复时），后add进去的值先被删，类似于出栈
	        System.out.println("删除指定元素个数："+jedis.lrem("collections", 2, "HashMap"));
	        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
	        System.out.println("删除下表0-3区间之外的元素："+jedis.ltrim("collections", 0, 3));
	        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
	        System.out.println("collections列表出栈（左端）："+jedis.lpop("collections"));
	        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
	        System.out.println("collections添加元素，从列表右端，与lpush相对应："+jedis.rpush("collections", "EnumMap"));
	        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
	        System.out.println("collections列表出栈（右端）："+jedis.rpop("collections"));
	        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
	        System.out.println("修改collections指定下标1的内容："+jedis.lset("collections", 1, "LinkedArrayList"));
	        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
	        System.out.println("===============================");
	        System.out.println("collections的长度："+jedis.llen("collections"));
	        System.out.println("获取collections下标为2的元素："+jedis.lindex("collections", 2));
	        System.out.println("===============================");
	        jedis.lpush("sortedList", "3","6","2","0","7","4");
	        System.out.println("sortedList排序前："+jedis.lrange("sortedList", 0, -1));
	        System.out.println(jedis.sort("sortedList"));
	        System.out.println("sortedList排序后："+jedis.lrange("sortedList", 0, -1));
	    }
	    
	    
	    /***
	     * set集合
	     */
	   
	    public void testSet() {
	    	JedisCluster jedis = getJedisPool();
	        System.out.println("============向集合中添加元素============");
	        System.out.println(jedis.sadd("eleSet", "e1","e2","e4","e3","e0","e8","e7","e5"));
	        System.out.println(jedis.sadd("eleSet", "e6"));
	        System.out.println(jedis.sadd("eleSet", "e6")); // 返回0，集合中已经存在
	        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
	        System.out.println("删除一个元素e0："+jedis.srem("eleSet", "e0"));
	        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
	        System.out.println("删除两个元素e7和e6："+jedis.srem("eleSet", "e7","e6"));
	        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
	        System.out.println("随机的移除集合中的一个元素："+jedis.spop("eleSet"));
	        System.out.println("随机的移除集合中的一个元素："+jedis.spop("eleSet"));
	        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
	        System.out.println("eleSet中包含元素的个数："+jedis.scard("eleSet"));
	        System.out.println("e3是否在eleSet中："+jedis.sismember("eleSet", "e3"));
	        System.out.println("e1是否在eleSet中："+jedis.sismember("eleSet", "e1"));
	        System.out.println("e5是否在eleSet中："+jedis.sismember("eleSet", "e5"));

	        // 集群下并存会报错：redis.clients.jedis.exceptions.JedisClusterException: No way to dispatch this command to Redis Cluster because keys have different slots.
	        // Redis集群，从key1集合与key2集合并存、交集、差集，两个键经过crc16算法可能有不同的槽。
	        /*System.out.println("=================================");
	        System.out.println(jedis.sadd("eleSet1", "e1","e2","e4","e3","e0","e8","e7","e5"));
	        System.out.println(jedis.sadd("eleSet2", "e1","e2","e4","e3","e0","e8"));
	        System.out.println("将eleSet1中删除e1并存入eleSet3中："+jedis.smove("eleSet1", "eleSet3", "e1"));
	        System.out.println("将eleSet1中删除e2并存入eleSet3中："+jedis.smove("eleSet1", "eleSet3", "e2"));
	        System.out.println("eleSet1中的元素："+jedis.smembers("eleSet1"));
	        System.out.println("eleSet3中的元素："+jedis.smembers("eleSet3"));*/

	        /*System.out.println("============集合运算=================");
	        System.out.println("eleSet1中的元素："+jedis.smembers("eleSet1"));
	        System.out.println("eleSet2中的元素："+jedis.smembers("eleSet2"));
	        System.out.println("eleSet1和eleSet2的交集:"+jedis.sinter("eleSet1","eleSet2"));
	        System.out.println("eleSet1和eleSet2的并集:"+jedis.sunion("eleSet1","eleSet2"));
	        System.out.println("eleSet1和eleSet2的差集:"+jedis.sdiff("eleSet1","eleSet2"));*/
	        jedis.del("eleSet");
	        jedis.del("eleSet1");
	        jedis.del("eleSet2");
	        jedis.del("eleSet3");
	    }
	    
	    
	    /***
	     * 散列
	     */
	    @Test
	    public void testHash() {
	    	JedisCluster jedis = getJedisPool();
	        Map<String,String> map = new HashMap<String,String>();
	        map.put("key1","value1");
	        map.put("key2","value2");
	        map.put("key3","value3");
	        map.put("key4","value4");
	        jedis.hmset("hash",map);
	        jedis.hset("hash", "key5", "value5");
	        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));//return Map<String,String>
	        System.out.println("散列hash的所有键为："+jedis.hkeys("hash"));//return Set<String>
	        System.out.println("散列hash的所有值为："+jedis.hvals("hash"));//return List<String>
	        System.out.println("将key6保存的值加上一个整数，如果key6不存在则添加key6："+jedis.hincrBy("hash", "key6", 6));
	        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
	        System.out.println("将key6保存的值加上一个整数，如果key6不存在则添加key6："+jedis.hincrBy("hash", "key6", 3));
	        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
	        System.out.println("删除一个或者多个键值对："+jedis.hdel("hash", "key2"));
	        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
	        System.out.println("散列hash中键值对的个数："+jedis.hlen("hash"));
	        System.out.println("判断hash中是否存在key2："+jedis.hexists("hash","key2"));
	        System.out.println("判断hash中是否存在key3："+jedis.hexists("hash","key3"));
	        System.out.println("获取hash中的值："+jedis.hmget("hash","key3"));
	        System.out.println("获取hash中的值："+jedis.hmget("hash","key3","key4"));
	    }
	    
	    /**
	     * 有序集合
	     */
	   
	    public void testSortedSet() {
	    	JedisCluster jedis = getJedisPool();
	        Map<String,Double> map = new HashMap<String,Double>();
	        map.put("key2",1.2);
	        map.put("key3",4.0);
	        map.put("key4",5.0);
	        map.put("key5",0.2);
	        // 将一个或多个 member 元素及其 score 值加入到有序集 key 当中，如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值
	        // score 值可以是整数值或双精度浮点数
	        System.out.println(jedis.zadd("zset", 3,"key1"));
	        System.out.println(jedis.zadd("zset",map));
	        System.out.println("zset中的所有元素："+jedis.zrange("zset", 0, -1));
	        System.out.println("zset中的所有元素："+jedis.zrangeWithScores("zset", 0, -1));
	        System.out.println("zset中的所有元素："+jedis.zrangeByScore("zset", 0,100));
	        System.out.println("zset中的所有元素："+jedis.zrangeByScoreWithScores("zset", 0,100));
	        System.out.println("zset中key2的分值："+jedis.zscore("zset", "key2"));
	        System.out.println("zset中key2的排名："+jedis.zrank("zset", "key2"));
	        System.out.println("删除zset中的元素key3："+jedis.zrem("zset", "key3"));
	        System.out.println("zset中的所有元素："+jedis.zrange("zset", 0, -1));
	        System.out.println("zset中元素的个数："+jedis.zcard("zset"));
	        System.out.println("zset中分值在1-4之间的元素的个数："+jedis.zcount("zset", 1, 4));
	        System.out.println("key2的分值加上5："+jedis.zincrby("zset", 5, "key2"));
	        System.out.println("key3的分值加上4："+jedis.zincrby("zset", 4, "key3"));
	        System.out.println("zset中的所有元素："+jedis.zrange("zset", 0, -1));
	    }
	    
	    
	    /*
	     * redis.clients.jedis.exceptions.JedisClusterException:
	     *  No way to dispatch this command to Redis Cluster because keys have different slots.
	     */
	   
		   public void testKeys()throws InterruptedException{
			   
			   System.out.println("获取所有的key："+getJedisPool().mget("username","name"));
		   }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
}























