package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.command.ConsoleCommandManager;
import com.yangyang.webmvcdemo.netty.chat.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    private ConsoleCommandManager manager = new ConsoleCommandManager();
    private Scanner sc = new Scanner(System.in);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
//        loginConsoleCommand.exec(sc,ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        log.info("login response ... {}, channel: {}",msg,ctx.channel());
        if(msg.isSuccess()){
            System.out.println(msg.getUserId()+" 登录成功! "+ctx.channel());
//            SessionUtils.markLogin(ctx.channel(),msg.getUserId());
        }else {
            System.out.println(msg.getUserId()+" 登录失败!,请重新登录! 原因: "+msg.getMessage());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭 ... ");
    }
}
