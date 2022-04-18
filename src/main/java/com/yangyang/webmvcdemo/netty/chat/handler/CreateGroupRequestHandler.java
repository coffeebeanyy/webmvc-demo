package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.codec.SessionUtils;
import com.yangyang.webmvcdemo.netty.chat.packet.CreateGroupRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.CreateGroupResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIdList = msg.getUserIdList();
        List<String> userNameList = new ArrayList<>();

        //1. create channel group
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        //2. filter channel
        for(String userId : userIdList){
            Channel channel = SessionUtils.getChannel(userId);
            if(channel != null){
                channelGroup.add(channel);
                userNameList.add(SessionUtils.getSession(channel).getUserName());

            }
        }
        //3. create group response
        CreateGroupResponsePacket packet = new CreateGroupResponsePacket();
        packet.setGroupId(SessionUtils.getGroupId());
        packet.setSuccess(true);
        packet.setUserNameList(userNameList);
        //4. mark group
        SessionUtils.markGroup(packet.getGroupId(),channelGroup);

        channelGroup.writeAndFlush(packet);
        System.out.println("group create success! group member: "+userNameList);
    }
}
