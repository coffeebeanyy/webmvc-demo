package com.yangyang.webmvcdemo.netty.chat.command;

import com.yangyang.webmvcdemo.netty.chat.packet.ListMembersGroupRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.channel.Channel;

import java.util.Scanner;

public class ListGroupMembersGroupCommand implements ConsoleCommand{
    @Override
    public Packet exec(Scanner scanner, Channel channel) {
        ListMembersGroupRequestPacket packet = new ListMembersGroupRequestPacket();
        System.out.println("输入 group_id 查看群成员:  ");
        int groupId = scanner.nextInt();
        packet.setGroupId(groupId);
        return packet;
    }
}
