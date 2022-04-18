package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.codec.SessionUtils;
import com.yangyang.webmvcdemo.netty.chat.packet.QuitGroupRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        int groupId = msg.getGroupId();

        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        if(channelGroup != null){
            channelGroup.remove(ctx.channel());
        }

        QuitGroupResponsePacket packet = new QuitGroupResponsePacket();
        packet.setGroupId(groupId);
        packet.setSuccess(true);

        ctx.channel().writeAndFlush(packet);
    }
}
