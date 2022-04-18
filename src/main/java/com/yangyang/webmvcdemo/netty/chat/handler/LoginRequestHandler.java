package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.codec.SessionUtils;
import com.yangyang.webmvcdemo.netty.chat.packet.LoginRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        log.info("LoginRequestHandler: {}, channel: {}",msg,ctx.channel());

//        LoginResponsePacket responsePacket = new LoginResponsePacket();
//        if(msg.getUsername().equals("hello") && msg.getPassword().equals("world")){
//            responsePacket.setSuccess(true);
//            responsePacket.setMessage("登录成功!");
//        }
//        ctx.channel().writeAndFlush(responsePacket);
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        String password = msg.getPassword();
        responsePacket.setUserId(msg.getUserId());
        if(!password.equals("123123")){
            responsePacket.setSuccess(false);
            responsePacket.setMessage("用户密码输入错误!");
        }else {
            String userId = msg.getUserId();
            responsePacket.setUserId(userId);
            responsePacket.setSuccess(true);
            SessionUtils.bindSession(SessionUtils.of(userId),ctx.channel());
        }
        log.info("server responsePacket: "+responsePacket);
        //send
        ctx.channel().writeAndFlush(responsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtils.unbindSession(ctx.channel());
    }
}
