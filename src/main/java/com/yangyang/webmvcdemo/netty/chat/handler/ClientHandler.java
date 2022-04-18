package com.yangyang.webmvcdemo.netty.chat.handler;

import cn.hutool.core.date.DateUtil;
import com.yangyang.webmvcdemo.netty.chat.codec.LoginUtil;
import com.yangyang.webmvcdemo.netty.chat.codec.PacketCodec;
import com.yangyang.webmvcdemo.netty.chat.packet.LoginRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.LoginResponsePacket;
import com.yangyang.webmvcdemo.netty.chat.packet.MessageResponsePacket;
import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date()+" : 客户端开始登录!");
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setPassword("123123");

        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), packet);
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;
            System.out.println(DateUtil.now()+" : "+responsePacket.getMessage()+" --> "+responsePacket.isSuccess());
            if(responsePacket.isSuccess()){
                LoginUtil.markAsLogin(ctx.channel());
            }
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket responsePacket = (MessageResponsePacket) packet;
            log.info("[收到服务端消息]: {}",responsePacket.getMessage());
        }
    }
}
