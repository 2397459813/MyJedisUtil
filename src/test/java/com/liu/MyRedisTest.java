package com.liu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.ICacheService;
import service.impl.CacheServiceImpl;

public class MyRedisTest {
	private  static final Logger LOGGER= LoggerFactory.getLogger(MyRedisTest.class);
	private ICacheService service = new CacheServiceImpl() ;

	public ICacheService getService() {
		return service;
	}

	public void setService(ICacheService service) {
		this.service = service;
	}
	
	
	public void  setString() throws Exception{
		LOGGER.info("����setString--->start");
		boolean flag = service.setString("score", "100");
		LOGGER.info("����setString���=[{}]",flag);
		
	}
	
	public void isExist() throws Exception{
		LOGGER.info("����isExist--->start");
		boolean flag = service.isExist("score");
		LOGGER.info("����isExist���=[{}]",flag);
	}
	
	
	public void getString() throws Exception{
		LOGGER.info("����getString--->start");
		String value = service.getString("mylist");
		LOGGER.info("����getString���=[{}]",value);
	}
	
	
	public void ttl() throws Exception{
		LOGGER.info("����ttl--->start");
		Long value = service.ttl("score");
		LOGGER.info("����ttl���=[{}]",value);
	}
	
	
	public void expire() throws Exception{
		LOGGER.info("����expire--->start");
		Long value = service.expire("score",200);
		LOGGER.info("����expire���=[{}]",value);
	}
	
	public void setObject() throws Exception{
		LOGGER.info("����setObject--->start");
		Map<String,Object> item = new HashMap<String,Object>();
		item.put("name", "liu");
		item.put("age", 28);
		boolean value = service.setObject("objectest",item);
		LOGGER.info("����setObject���=[{}]",value);
	}
	
	public void getObject() throws Exception{
		LOGGER.info("����getObject--->start");
		Object object = service.getObject("");
		LOGGER.info("����getObject���=[{}]",object);
	}
	

	
	public void setex() throws Exception{
		LOGGER.info("����setex--->start");
		Object object = service.setex("objectest1".getBytes(),"objectest1value".getBytes(),2000);
		LOGGER.info("����setex���=[{}]",object);
	}
	
	
	
	public void incrBy() throws Exception{
		LOGGER.info("����incrBy--->start");
		Object object = service.incrBy("pass",100);
		LOGGER.info("����incrBy���=[{}]",object);
	}
	
	public void llen() throws Exception{
		LOGGER.info("����llen--->start");
		Long object = service.llen("mylist".getBytes());
		LOGGER.info("����llen���=[{}]",object);
	}
	
	
	
	public void lpush() throws Exception{
		LOGGER.info("����lpush--->start");
		Long object = service.lpush("mylist","sex","seven");
		LOGGER.info("����lpush���=[{}]",object);
	}
	
	public void rpush() throws Exception{
		LOGGER.info("����rpush--->start");
		Long object = service.rpush("mylist","eight","nine");
		LOGGER.info("����rpush���=[{}]",object);
	}
	
	public void lrange() throws Exception{
		LOGGER.info("����lrange--->start");
		List<String> item  = service.lrange("mylist",1,3);
		LOGGER.info("����lrange���=[{}]",item);
	}
	
	
	
	public void lindex() throws Exception{
		LOGGER.info("����lindex--->start");
		String item  = service.lindex("mylist",1);
		LOGGER.info("����lindex���=[{}]",item);
	}
	
	
	public void lpop() throws Exception{
		LOGGER.info("����lpop--->start");
		String item  = service.lpop("mylist");
		LOGGER.info("����lpop���=[{}]",item);
	}
	
	
	public void putMap() throws Exception{
		LOGGER.info("����putMap--->start");
		Map<String,String> item = new HashMap<String,String>();
		item.put("name", "С��");
		item.put("age", "18");
		String result  = service.putMap("myhash1",item);
		LOGGER.info("����putMap���=[{}]",result);
	}
	
	
	public void getMap() throws Exception{
		LOGGER.info("����getMap--->start");
		Map<String, String> result  = service.getMap("myhash1");
		LOGGER.info("����getMap���=[{}]",result.get("name"));
	}
	
	
	public void hset() throws Exception{
		LOGGER.info("����hset--->start");
		Long result  = service.hset("myhash1".getBytes(),"age".getBytes(),"16".getBytes());
		LOGGER.info("����hset���=[{}]",result);
	}
	
	public void hget() throws Exception{
		LOGGER.info("����hget--->start");
		String result  = service.hget("myhash1","age");
		LOGGER.info("����hget���=[{}]",result);
	}
	
	
	public void hlen() throws Exception{
		LOGGER.info("����hlen--->start");
		Long result  = service.hlen("myhash1");
		LOGGER.info("����hlen���=[{}]",result);
	}
	
	
	public void hkeys() throws Exception{
		LOGGER.info("����hkeys--->start");
		Set<String> result  = service.hkeys("myhash1");
		LOGGER.info("����hkeys���=[{}]",result);
	}
	
	public void sadd() throws Exception{
		LOGGER.info("����sadd--->start");
		Long result  = service.sadd("myset","java","c++");
		LOGGER.info("����sadd���=[{}]",result);
	}
	

	
	public void smembers() throws Exception{
		LOGGER.info("����smembers--->start");
		Set<String> result  = service.smembers("myset");
		LOGGER.info("����smembers���=[{}]",result);
	}
	
	
	
	
	
	public void zadd() throws Exception{
		LOGGER.info("����zadd--->start");
		Long result  = service.zadd("myzset",100,"liu");
		LOGGER.info("����zadd���=[{}]",result);
	}
	
	
	
	public void zrevrangeByScore() throws Exception{
		LOGGER.info("����zrevrangeByScore--->start");
		Set<String> result  = service.zrevrangeByScore("myzset",150,50);
		LOGGER.info("����zrevrangeByScore���=[{}]",result);
	}
	

	public void zremrangeByScore() throws Exception{
		LOGGER.info("����zremrangeByScore--->start");
		Long result  = service.zremrangeByScore("myzset",50,150);
		LOGGER.info("����zremrangeByScore���=[{}]",result);
	}
	
	
	
	
	public void zscore() throws Exception{
		LOGGER.info("����zscore--->start");
		Double result  = service.zscore("myzset","rabbit");
		LOGGER.info("����zscore���=[{}]",result);
	}
	
	@Test
	public void zrevrange() throws Exception{
		LOGGER.info("����zrevrange------>start");
		Set<String> result  = service.zrevrange("myzset",1,2);
		LOGGER.info("����zrevrange���=[{}]",result);
	}
	
	
	
	
	
	
	
	
	
	

}
