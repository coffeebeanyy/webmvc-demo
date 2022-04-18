package com.yangyang.webmvcdemo.netty.chat.packet;

import com.yangyang.webmvcdemo.netty.chat.command.Command;
import lombok.Data;

@Data
public class JoinGroupResponsePacket extends Packet{
    private int groupId;
    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
