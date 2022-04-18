package com.yangyang.webmvcdemo.netty.chat.packet;

import com.yangyang.webmvcdemo.netty.chat.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet{
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
