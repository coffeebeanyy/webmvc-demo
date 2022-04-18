package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.command.Command;
import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

public class IMHandler extends SimpleChannelInboundHandler<Packet> {
    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte,SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler(){
        handlerMap = new HashMap<>();
        handlerMap.put(Command.MESSAGE_REQUEST,new MessageRequestHandler());
        handlerMap.put(Command.CREATE_GROUP_REQUEST,new CreateGroupRequestHandler());
        handlerMap.put(Command.JOIN_GROUP_RESPONSE,new JoinGroupRequestHandler());
        handlerMap.put(Command.QUIT_GROUP_RESPONSE,new QuitGroupRequestHandler());
        handlerMap.put(Command.LIST_MEMBER_GROUP_RESPONSE,new ListMemberGroupRequestHandler());
        handlerMap.put(Command.LOGIN_REQUEST,new LoginRequestHandler());
        handlerMap.put(Command.GROUP_MESSAGE_REQUEST,new GroupMessageRequestHandler());

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx,msg);
    }
}
