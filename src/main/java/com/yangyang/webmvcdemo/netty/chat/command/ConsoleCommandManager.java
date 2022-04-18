package com.yangyang.webmvcdemo.netty.chat.command;

import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand{

    private Map<String,ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager(){
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("send",new SendToUserConsoleCommand());
        consoleCommandMap.put("create",new CreateGroupConsoleCommand());
        consoleCommandMap.put("quit",new QuitGroupCommand());
        consoleCommandMap.put("list",new ListGroupMembersGroupCommand());
        consoleCommandMap.put("join",new JoinGroupCommand());
        consoleCommandMap.put("group",new GroupMessageCommand());
    }

    @Override
    public Packet exec(Scanner scanner, Channel channel) {
        String commandStr = scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(commandStr);
        if(consoleCommand != null){
            Packet packet = consoleCommand.exec(scanner, channel);
            channel.writeAndFlush(packet);
        }else {
            System.err.println("无法识别[ "+commandStr+" ]指令,请重新输入!");
        }
        return null;
    }
}
