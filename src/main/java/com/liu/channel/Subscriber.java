package com.liu.channel;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {

    public Subscriber(){}
    @Override
    public void onMessage(String channel, String message) {       //�յ���Ϣ�����
        System.out.println(String.format("receive redis published message, channel %s, message %s", channel, message));
    }
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {    //������Ƶ�������
        System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
                channel, subscribedChannels));
    }
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {   //ȡ������ �����
        System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d",
                channel, subscribedChannels));

    }
}
