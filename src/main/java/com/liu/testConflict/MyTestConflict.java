package com.liu.testConflict;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import util.RedisUtil;

public class MyTestConflict {
	private  static final Logger log= LoggerFactory.getLogger(MyTestConflict.class);
	
	public static void main(String[] args) {
		System.out.println("");
	}
	
	
	public static  void testLua() {
		Jedis jedis = RedisUtil.getJedis();
		System.out.println(jedis.ping());
		String str1 = "return redis.pcall('set',KEYS[1],'lua script')";//���ü�k1��ֵΪbar
		Object evalresult = jedis.eval(str1, 1,"lua");
		System.out.println(evalresult);
		System.out.println(jedis.get("lua"));//�鿴ִ�����
	}
	
	
	public static void testLua2() {
		Jedis jedis = RedisUtil.getJedis();
		List<String> keys = new ArrayList<>();
		List<String> vals = new ArrayList<>();
		keys.add("name");
		keys.add("foo");
		//�ô����������Ի��浽������������ÿ�ΰ�lua�ű������ݴ���ȥ
		String lua = "local tab={}  for i=1,#KEYS do  tab[i] = redis.call('get',KEYS[i]) end return tab";
		String scriptLoad = jedis.scriptLoad(lua);
		log.info(scriptLoad);
		Object evalsha = jedis.evalsha(scriptLoad, keys, vals);
		log.info(evalsha+"");
	}
	
	
	public static void testLua3() {
		Jedis jedis = RedisUtil.getJedis();
		List<String> keys = new ArrayList<>();
		List<String> vals = new ArrayList<>();
		keys.add("name");
		keys.add("foo");
		//�ô����������Ի��浽������������ÿ�ΰ�lua�ű������ݴ���ȥ
		String lua = "local tab={}  for i=1,#KEYS do  tab[i] = redis.call('get',KEYS[i]) end return tab";
		String scriptLoad = jedis.scriptLoad(lua);
		log.info(scriptLoad);
		Object evalsha = jedis.evalsha(scriptLoad, keys, vals);
		log.info(evalsha+"");
	}
	
}
