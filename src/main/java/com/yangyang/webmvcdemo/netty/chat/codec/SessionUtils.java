package com.yangyang.webmvcdemo.netty.chat.codec;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionUtils {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<Integer, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();
    private static final AtomicInteger groupId = new AtomicInteger(1000);

    public static int getGroupId() {
        return groupId.getAndIncrement();
    }

    public static Session of(String userId){
        Session session = new Session();
        session.setUserId(userId);
        session.setUserName(userId);
        return session;
    }
    public static void bindSession(Session session,Channel channel){
        userIdChannelMap.put(session.getUserId(),channel);
        channel.attr(Attributes.SESSION).set(session);
        System.out.println("session map: "+userIdChannelMap);
    }
    public static void markLogin(Channel channel,String userId){
        channel.attr(Attributes.SESSION).set(SessionUtils.of(userId));
    }
    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel){
        return channel.attr(Attributes.SESSION).get();
    }
    public static Session getSession(String userId){
        return getSession(userIdChannelMap.get(userId));
    }
    public static Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }

    public static ChannelGroup getChannelGroup(int groupId){
        return channelGroupMap.get(groupId);
    }
    public static void markGroup(int groupId,ChannelGroup group){
        channelGroupMap.put(groupId,group);
    }
    public static void unbindSession(Channel channel){
        System.out.println("remove channel: "+channel);
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }
}
