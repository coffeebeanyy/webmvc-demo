package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.packet.ListMembersGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ListMemberGroupResponseHandler extends SimpleChannelInboundHandler<ListMembersGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListMembersGroupResponsePacket msg) throws Exception {
        if(msg.isSuccess()){
            System.out.println("群: "+msg.getGroupId()+" 成员: "+msg.getNameList());
        }
    }
}
