package com.yangyang.webmvcdemo.netty.chat.command;

import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import com.yangyang.webmvcdemo.netty.chat.packet.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class QuitGroupCommand implements ConsoleCommand{
    @Override
    public Packet exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket packet = new QuitGroupRequestPacket();
        System.out.println("输入 group_id 退出群聊:  ");
        int groupId = scanner.nextInt();
        packet.setGroupId(groupId);
        return packet;
    }
}
