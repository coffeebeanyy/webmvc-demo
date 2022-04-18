package com.yangyang.webmvcdemo.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Client {
    private static int MAX_RETRY = 3;
    private static NioEventLoopGroup g = new NioEventLoopGroup();

    private static void connect(Bootstrap b,String host,int port,int retry){
        b.connect(host,port).addListener(f -> {
            int retryInternal = retry - 1;
            if(f.isSuccess()){
                System.out.println("连接成功!");
            }else if(retry == 0){
                System.out.println("重试次数已用完,放弃连接!");
                g.shutdownGracefully();
                b.clone();
            }else {
                int order = (MAX_RETRY-retry)+1;
                int delay = 1 << order;
                System.out.println(new Date()+" :连接失败,第 "+order+" 次重连...");
                b.config().group().schedule(() -> connect(b, host, port, retryInternal),delay, TimeUnit.SECONDS);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        Bootstrap b = new Bootstrap();
        b.group(g).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
//                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                })
                .attr(AttributeKey.newInstance("clientName"),"nettyClient")
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true);

        connect(b,"127.0.0.1",8081,MAX_RETRY);
    }
}
