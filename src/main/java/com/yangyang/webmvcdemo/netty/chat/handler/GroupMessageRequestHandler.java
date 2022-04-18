package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.codec.SessionUtils;
import com.yangyang.webmvcdemo.netty.chat.packet.GroupMessageRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        int groupId = msg.getFromGroupId();

        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setFromGroupId(groupId);
        responsePacket.setFromUser(SessionUtils.getSession(ctx.channel()).getUserId());
        responsePacket.setMessage(msg.getMessage());

        //send group
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);

        channelGroup.writeAndFlush(responsePacket);

    }
}
