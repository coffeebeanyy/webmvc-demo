package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.packet.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

public class HeatBeatTimerHandler extends ChannelInboundHandlerAdapter {
    private static final int HEAT_BEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx){
        ctx.executor().schedule(() -> {
            if(ctx.channel().isActive()){
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        },HEAT_BEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
