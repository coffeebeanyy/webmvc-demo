package com.yangyang.webmvcdemo.netty.chat.packet;

import com.yangyang.webmvcdemo.netty.chat.command.Command;
import lombok.Data;

@Data
public class GroupMessageResponsePacket extends Packet{
    private int fromGroupId;
    private String message;
    private String fromUser;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
