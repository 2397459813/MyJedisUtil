package com.liu.jedis;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import util.RedisUtil;

public class TestLua {
	private  static final Logger log= LoggerFactory.getLogger(TestLua.class);

	public static void main(String[] args) {
		testLua2();
	}
	
	public static  void testLua() {
		Jedis jedis = RedisUtil.getJedis();
		System.out.println(jedis.ping());
		String str1 = "return redis.pcall('set',KEYS[1],'lua script')";//设置键k1的值为bar
		Object evalresult = jedis.eval(str1, 1,"lua");
		System.out.println(evalresult);
		System.out.println(jedis.get("lua"));//查看执行情况
	}
	
	//添加了一行注释
	public static void testLua2() {
		Jedis jedis = RedisUtil.getJedis();
		List<String> keys = new ArrayList<>();
		List<String> vals = new ArrayList<>();
		keys.add("name1111111111111111*************************");
		keys.add("foo");
		keys.add("foo1***************************************8*******");
		//好处：这样可以缓存到服务器，不用每次把lua脚本的内容传过去
		String lua = "local tab={}  for i=1,#KEYS do  tab[i] = redis.call('get',KEYS[i]) end return tab";
		String scriptLoad = jedis.scriptLoad(lua);
		log.info(scriptLoad);
		Object evalsha = jedis.evalsha(scriptLoad, keys, vals);
		log.info(evalsha+"");
	}
	
	
	
	
}
