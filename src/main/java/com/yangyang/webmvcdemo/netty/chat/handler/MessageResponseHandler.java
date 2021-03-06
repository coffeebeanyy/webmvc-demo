package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        log.info("message response: {}",msg);
    }
}
