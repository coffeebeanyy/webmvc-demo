package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.packet.HeartBeatRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HearBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        System.out.println("服务端心跳包: "+msg);
        ctx.channel().writeAndFlush(new HeartBeatResponsePacket());
    }
}
