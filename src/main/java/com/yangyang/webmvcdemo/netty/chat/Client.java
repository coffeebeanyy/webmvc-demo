package com.yangyang.webmvcdemo.netty.chat;

import com.yangyang.webmvcdemo.netty.chat.codec.PacketCodecHandler;
import com.yangyang.webmvcdemo.netty.chat.codec.SessionUtils;
import com.yangyang.webmvcdemo.netty.chat.command.ConsoleCommandManager;
import com.yangyang.webmvcdemo.netty.chat.command.LoginConsoleCommand;
import com.yangyang.webmvcdemo.netty.chat.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
    private static int MAX_RETRY = 3;
    private static NioEventLoopGroup g = new NioEventLoopGroup();

    private static void connect(Bootstrap b,String host,int port,int retry){
        b.connect(host,port).addListener(f -> {
            int retryInternal = retry - 1;
            if(f.isSuccess()){
                System.out.println("连接成功! 开启控制台线程 ... ");
                startConsoleThread(((ChannelFuture)f).channel());
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

    private static void waitForResponse(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static void startConsoleThread(Channel channel){
        Scanner sc = new Scanner(System.in);
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        ConsoleCommandManager commandManager = new ConsoleCommandManager();
        new Thread(()->{
            while (!Thread.interrupted()){
                System.out.println("login: "+ SessionUtils.hasLogin(channel));
//                if(!SessionUtils.hasLogin(channel)){
//                    System.out.println("输入用户名: ");
//                    String line = sc.nextLine();
//                    String[] split = line.split(" ");
//                    LoginRequestPacket requestPacket = new LoginRequestPacket();
//                    requestPacket.setUserId(split[0]);
//                    requestPacket.setPassword(split[1]);
//                    channel.writeAndFlush(requestPacket);
//
//                    waitForResponse();
//                }
//                else {
//                    Session session = SessionUtils.getSession(channel);
//                    String[] split = sc.nextLine().split(" ");
//                    String toUserId = split[0];
//                    String message = split[1];
//                    channel.writeAndFlush(new MessageRequestPacket(session.getUserId(),toUserId,message));
//                }
                if(!SessionUtils.hasLogin(channel)){
                    loginConsoleCommand.exec(sc,channel);
                    waitForResponse();
                }else {
                    commandManager.exec(sc,channel);
                }
            }
        }).start();
    }
    public static void main(String[] args) throws InterruptedException {
        Bootstrap b = new Bootstrap();
        b.group(g).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
//                        ch.pipeline().addLast(new ClientHandler());
                        ChannelPipeline p = ch.pipeline();
//                        p.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,7,4));
                        p.addLast(PacketCodecHandler.INSTANCE);
                        p.addLast(new SpliterHandler());
                        p.addLast(new HearBeatResponseHandler());
                        p.addLast(new HeatBeatTimerHandler());
                        p.addLast(new LoginResponseHandler());
                        p.addLast(new MessageResponseHandler());
                        p.addLast(new CreateGroupResponseHandler(),new QuitGroupResponseHandler(),new JoinGroupResponseHandler(),new ListMemberGroupResponseHandler());
                        p.addLast(new GroupMessageResponseHandler());
                    }
                })
                .attr(AttributeKey.newInstance("clientName"),"nettyClient")
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true);

        connect(b,"127.0.0.1",8081,MAX_RETRY);
    }
}
