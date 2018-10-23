package com.liu.channel;

import redis.clients.jedis.JedisPool;
import util.RedisUtil;

public class PubSubDemo {

    public static void main( String[] args )
    {
        // ����redis�����
        JedisPool jedisPool = RedisUtil.getJedisPool();
        
        System.out.println(String.format("redis pool is starting, redis ip %s, redis port %d", "127.0.0.1", 6379));

        SubThread subThread = new SubThread(jedisPool);  //������
        subThread.start();

        Publisher publisher = new Publisher(jedisPool);    //������
        publisher.start();
    }
}