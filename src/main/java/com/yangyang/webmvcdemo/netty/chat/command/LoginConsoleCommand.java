package com.yangyang.webmvcdemo.netty.chat.command;

import com.yangyang.webmvcdemo.netty.chat.packet.LoginRequestPacket;
import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand{
    @Override
    public Packet exec(Scanner scanner, Channel channel) {
        System.out.println("输入用户名和密码进行登录: ");
        String user = scanner.nextLine();
        String[] split = user.split(" ");
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(split[0]);
        packet.setPassword(split[1]);

        channel.writeAndFlush(packet);
        return packet;
    }
}
