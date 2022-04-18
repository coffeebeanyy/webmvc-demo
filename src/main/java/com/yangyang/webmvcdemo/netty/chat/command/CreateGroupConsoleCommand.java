package com.yangyang.webmvcdemo.netty.chat.command;

import com.yangyang.webmvcdemo.netty.chat.packet.CreateGroupRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand{
    @Override
    public Packet exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket packet = new CreateGroupRequestPacket();
        System.out.println("[拉人入群] 输入userId列表,逗号分割: ");
        String userIds = scanner.next();
        packet.setUserIdList(Arrays.asList(userIds.split(",")));
        System.out.println("send CreateGroupRequestPacket: "+packet);
        return packet;
    }
}
