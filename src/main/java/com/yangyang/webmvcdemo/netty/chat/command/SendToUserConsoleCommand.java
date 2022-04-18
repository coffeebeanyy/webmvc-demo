package com.yangyang.webmvcdemo.netty.chat.command;

import com.yangyang.webmvcdemo.netty.chat.packet.MessageRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand{
    @Override
    public Packet exec(Scanner scanner, Channel channel) {
        String[] split = scanner.next().split(" ");
        String sendToUser = split[0];
        String message = split[1];

        MessageRequestPacket packet = new MessageRequestPacket();
        packet.setToUserId(sendToUser);
        packet.setMessage(message);

        channel.writeAndFlush(packet);

        return packet;
    }
}
