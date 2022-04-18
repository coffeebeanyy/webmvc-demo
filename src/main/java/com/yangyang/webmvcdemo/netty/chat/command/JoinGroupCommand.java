package com.yangyang.webmvcdemo.netty.chat.command;

import com.yangyang.webmvcdemo.netty.chat.packet.JoinGroupRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinGroupCommand implements ConsoleCommand{
    @Override
    public Packet exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket packet = new JoinGroupRequestPacket();
        System.out.println("输入 group_id 加入群聊:  ");
        int groupId = scanner.nextInt();
        packet.setGroupId(groupId);
        return packet;
    }
}
