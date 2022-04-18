package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.codec.Session;
import com.yangyang.webmvcdemo.netty.chat.codec.SessionUtils;
import com.yangyang.webmvcdemo.netty.chat.packet.ListMembersGroupRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.ListMembersGroupResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

public class ListMemberGroupRequestHandler extends SimpleChannelInboundHandler<ListMembersGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListMembersGroupRequestPacket msg) throws Exception {
        int groupId = msg.getGroupId();

        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        List<String> nameList = new ArrayList<>();
        if(channelGroup != null){
            for(Channel channel : channelGroup){
                Session session = SessionUtils.getSession(channel);
                nameList.add(session.getUserId());
            }
        }

        ListMembersGroupResponsePacket packet = new ListMembersGroupResponsePacket();
        packet.setGroupId(groupId);
        packet.setSuccess(true);
        packet.setNameList(nameList);

        ctx.channel().writeAndFlush(packet);
    }
}
