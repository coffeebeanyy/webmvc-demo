package com.yangyang.webmvcdemo.netty.chat.command;

import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.channel.Channel;

import java.util.Scanner;

public interface ConsoleCommand {
    Packet exec(Scanner scanner, Channel channel);
}
