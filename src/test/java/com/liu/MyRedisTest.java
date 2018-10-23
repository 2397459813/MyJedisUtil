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
		LOGGER.info("²âÊÔsetString--->start");
		boolean flag = service.setString("score", "100");
		LOGGER.info("²âÊÔsetString½á¹û=[{}]",flag);
		
	}
	
	public void isExist() throws Exception{
		LOGGER.info("²âÊÔisExist--->start");
		boolean flag = service.isExist("score");
		LOGGER.info("²âÊÔisExist½á¹û=[{}]",flag);
	}
	
	
	public void getString() throws Exception{
		LOGGER.info("²âÊÔgetString--->start");
		String value = service.getString("mylist");
		LOGGER.info("²âÊÔgetString½á¹û=[{}]",value);
	}
	
	
	public void ttl() throws Exception{
		LOGGER.info("²âÊÔttl--->start");
		Long value = service.ttl("score");
		LOGGER.info("²âÊÔttl½á¹û=[{}]",value);
	}
	
	
	public void expire() throws Exception{
		LOGGER.info("²âÊÔexpire--->start");
		Long value = service.expire("score",200);
		LOGGER.info("²âÊÔexpire½á¹û=[{}]",value);
	}
	
	public void setObject() throws Exception{
		LOGGER.info("²âÊÔsetObject--->start");
		Map<String,Object> item = new HashMap<String,Object>();
		item.put("name", "liu");
		item.put("age", 28);
		boolean value = service.setObject("objectest",item);
		LOGGER.info("²âÊÔsetObject½á¹û=[{}]",value);
	}
	
	public void getObject() throws Exception{
		LOGGER.info("²âÊÔgetObject--->start");
		Object object = service.getObject("");
		LOGGER.info("²âÊÔgetObject½á¹û=[{}]",object);
	}
	

	
	public void setex() throws Exception{
		LOGGER.info("²âÊÔsetex--->start");
		Object object = service.setex("objectest1".getBytes(),"objectest1value".getBytes(),2000);
		LOGGER.info("²âÊÔsetex½á¹û=[{}]",object);
	}
	
	
	
	public void incrBy() throws Exception{
		LOGGER.info("²âÊÔincrBy--->start");
		Object object = service.incrBy("pass",100);
		LOGGER.info("²âÊÔincrBy½á¹û=[{}]",object);
	}
	
	public void llen() throws Exception{
		LOGGER.info("²âÊÔllen--->start");
		Long object = service.llen("mylist".getBytes());
		LOGGER.info("²âÊÔllen½á¹û=[{}]",object);
	}
	
	
	
	public void lpush() throws Exception{
		LOGGER.info("²âÊÔlpush--->start");
		Long object = service.lpush("mylist","sex","seven");
		LOGGER.info("²âÊÔlpush½á¹û=[{}]",object);
	}
	
	public void rpush() throws Exception{
		LOGGER.info("²âÊÔrpush--->start");
		Long object = service.rpush("mylist","eight","nine");
		LOGGER.info("²âÊÔrpush½á¹û=[{}]",object);
	}
	
	public void lrange() throws Exception{
		LOGGER.info("²âÊÔlrange--->start");
		List<String> item  = service.lrange("mylist",1,3);
		LOGGER.info("²âÊÔlrange½á¹û=[{}]",item);
	}
	
	
	
	public void lindex() throws Exception{
		LOGGER.info("²âÊÔlindex--->start");
		String item  = service.lindex("mylist",1);
		LOGGER.info("²âÊÔlindex½á¹û=[{}]",item);
	}
	
	
	public void lpop() throws Exception{
		LOGGER.info("²âÊÔlpop--->start");
		String item  = service.lpop("mylist");
		LOGGER.info("²âÊÔlpop½á¹û=[{}]",item);
	}
	
	
	public void putMap() throws Exception{
		LOGGER.info("²âÊÔputMap--->start");
		Map<String,String> item = new HashMap<String,String>();
		item.put("name", "Ğ¡·²");
		item.put("age", "18");
		String result  = service.putMap("myhash1",item);
		LOGGER.info("²âÊÔputMap½á¹û=[{}]",result);
	}
	
	
	public void getMap() throws Exception{
		LOGGER.info("²âÊÔgetMap--->start");
		Map<String, String> result  = service.getMap("myhash1");
		LOGGER.info("²âÊÔgetMap½á¹û=[{}]",result.get("name"));
	}
	
	
	public void hset() throws Exception{
		LOGGER.info("²âÊÔhset--->start");
		Long result  = service.hset("myhash1".getBytes(),"age".getBytes(),"16".getBytes());
		LOGGER.info("²âÊÔhset½á¹û=[{}]",result);
	}
	
	public void hget() throws Exception{
		LOGGER.info("²âÊÔhget--->start");
		String result  = service.hget("myhash1","age");
		LOGGER.info("²âÊÔhget½á¹û=[{}]",result);
	}
	
	
	public void hlen() throws Exception{
		LOGGER.info("²âÊÔhlen--->start");
		Long result  = service.hlen("myhash1");
		LOGGER.info("²âÊÔhlen½á¹û=[{}]",result);
	}
	
	
	public void hkeys() throws Exception{
		LOGGER.info("²âÊÔhkeys--->start");
		Set<String> result  = service.hkeys("myhash1");
		LOGGER.info("²âÊÔhkeys½á¹û=[{}]",result);
	}
	
	public void sadd() throws Exception{
		LOGGER.info("²âÊÔsadd--->start");
		Long result  = service.sadd("myset","java","c++");
		LOGGER.info("²âÊÔsadd½á¹û=[{}]",result);
	}
	

	
	public void smembers() throws Exception{
		LOGGER.info("²âÊÔsmembers--->start");
		Set<String> result  = service.smembers("myset");
		LOGGER.info("²âÊÔsmembers½á¹û=[{}]",result);
	}
	
	
	
	
	
	public void zadd() throws Exception{
		LOGGER.info("²âÊÔzadd--->start");
		Long result  = service.zadd("myzset",100,"liu");
		LOGGER.info("²âÊÔzadd½á¹û=[{}]",result);
	}
	
	
	
	public void zrevrangeByScore() throws Exception{
		LOGGER.info("²âÊÔzrevrangeByScore--->start");
		Set<String> result  = service.zrevrangeByScore("myzset",150,50);
		LOGGER.info("²âÊÔzrevrangeByScore½á¹û=[{}]",result);
	}
	

	public void zremrangeByScore() throws Exception{
		LOGGER.info("²âÊÔzremrangeByScore--->start");
		Long result  = service.zremrangeByScore("myzset",50,150);
		LOGGER.info("²âÊÔzremrangeByScore½á¹û=[{}]",result);
	}
	
	
	
	
	public void zscore() throws Exception{
		LOGGER.info("²âÊÔzscore--->start");
		Double result  = service.zscore("myzset","rabbit");
		LOGGER.info("²âÊÔzscore½á¹û=[{}]",result);
	}
	
	@Test
	public void zrevrange() throws Exception{
		LOGGER.info("²âÊÔzrevrange------>start");
		Set<String> result  = service.zrevrange("myzset",1,2);
		LOGGER.info("²âÊÔzrevrange½á¹û=[{}]",result);
	}
	
	
	
	
	
	
	
	
	
	

}
