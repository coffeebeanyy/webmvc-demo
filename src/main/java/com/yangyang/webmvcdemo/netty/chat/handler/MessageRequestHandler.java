package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.codec.SessionUtils;
import com.yangyang.webmvcdemo.netty.chat.packet.MessageRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.MessageResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        log.info("message request: {}",msg);
//        MessageResponsePacket responsePacket = new MessageResponsePacket();
//        responsePacket.setMessage("[server send] "+msg.getMessage());

        //消息发送方的会话信息
//        Session session = SessionUtils.getSession(msg.getFromUserId());

        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setMessage(msg.getMessage());
        responsePacket.setFromUserId(msg.getFromUserId());
        responsePacket.setFromUserId(msg.getToUserId());

        //获取接收方的会话消息
        Channel toUserChannel = SessionUtils.getChannel(msg.getToUserId());
        //send
        if(toUserChannel != null){
            toUserChannel.writeAndFlush(responsePacket);
        }else {
            System.out.println("==> ["+msg.getToUserId()+"] 不在线,消息发送失败!");
            ctx.channel().writeAndFlush(responsePacket);
        }
    }
}
