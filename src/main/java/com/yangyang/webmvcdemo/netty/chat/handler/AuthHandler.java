package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.codec.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!SessionUtils.hasLogin(ctx.channel())){
            ctx.channel().close();
        }else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(SessionUtils.hasLogin(ctx.channel())){
            System.out.println("当前登录验证完毕,无需再次验证, AuthHandler被移除");
        }else {
            System.out.println("无登录验证,强制关闭连接!");
        }
    }
}
