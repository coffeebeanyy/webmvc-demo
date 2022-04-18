package com.yangyang.webmvcdemo.netty.chat.handler;

import cn.hutool.core.date.DateUtil;
import com.yangyang.webmvcdemo.netty.chat.codec.PacketCodec;
import com.yangyang.webmvcdemo.netty.chat.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        Packet packet = PacketCodec.INSTANCE.decode(buf);
        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            log.info("==> 用户开始登录: "+ctx.channel().remoteAddress());
            LoginResponsePacket responsePacket = new LoginResponsePacket();
            if(isLogin(loginRequestPacket)){
                //登录成功
                responsePacket.setVersion(loginRequestPacket.getVersion());
                responsePacket.setSuccess(true);
                responsePacket.setMessage("登录成功!");
            }else {
                //登录失败
                responsePacket.setSuccess(false);
                responsePacket.setMessage("登录失败!");
            }
            ByteBuf byteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(byteBuf);
        }else if(packet instanceof MessageRequestPacket){
            MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
            log.info("{} 收到客户端消息: {}", DateUtil.now(),requestPacket.getMessage());

            MessageResponsePacket responsePacket = new MessageResponsePacket();
            responsePacket.setMessage("[服务端回复]: "+requestPacket.getMessage());

            ctx.channel().writeAndFlush(PacketCodec.INSTANCE.encode(ctx.alloc(),responsePacket));
        }
    }

    private boolean isLogin(LoginRequestPacket loginRequestPacket) {
        return loginRequestPacket.getUserId().equals("coffee") && loginRequestPacket.getPassword().equals("123123");
    }
}
