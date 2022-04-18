package com.yangyang.webmvcdemo.netty.chat.command;

import com.yangyang.webmvcdemo.netty.chat.packet.GroupMessageRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.channel.Channel;

import java.util.Scanner;

public class GroupMessageCommand implements ConsoleCommand{
    @Override
    public Packet exec(Scanner scanner, Channel channel) {
        GroupMessageRequestPacket packet = new GroupMessageRequestPacket();
        System.out.println("输入群消息(格式fromUser groupId message): ");
        String fromUser = scanner.next();
        int groupId = scanner.nextInt();
        String message = scanner.next();
        packet.setFromUser(fromUser);
        packet.setFromGroupId(groupId);
        packet.setMessage(message);

        return packet;
    }
}
