package com.yangyang.webmvcdemo.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date()+" : 客户端写出数据");
        ctx.channel().writeAndFlush(getByteBuf(ctx));
    }
    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("hello world .... ".getBytes(StandardCharsets.UTF_8));
        return buffer;
    }
}
