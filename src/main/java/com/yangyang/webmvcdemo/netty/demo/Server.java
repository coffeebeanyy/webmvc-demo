package com.yangyang.webmvcdemo.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class Server {
    public static void main(String[] args) {
        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        b.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .attr(AttributeKey.newInstance("serverName"),"nettyServer")
                .childAttr(AttributeKey.newInstance("clientKey"),"clientValue")
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
//                        p.addLast(new StringDecoder()).addLast(new SimpleChannelInboundHandler<String>() {
//                            @Override
//                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//                                System.out.println("server read: "+msg);
//                            }
//                        });
                        p.addLast(new FirstServerHandler());
                    }
                }).bind(8081).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if(future.isSuccess()){
                            System.out.println("端口绑定成功!");
                        }else {
                            System.out.println("端口绑定失败!");
                        }
                    }
                });
    }
}
