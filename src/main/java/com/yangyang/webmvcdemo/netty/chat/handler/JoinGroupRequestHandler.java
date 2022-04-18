package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.codec.SessionUtils;
import com.yangyang.webmvcdemo.netty.chat.packet.JoinGroupRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        int groupId = msg.getGroupId();

        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        if(channelGroup != null){
            channelGroup.add(ctx.channel());
        }

        JoinGroupResponsePacket packet = new JoinGroupResponsePacket();
        packet.setGroupId(groupId);
        packet.setSuccess(true);
        ctx.channel().writeAndFlush(packet);
    }
}
