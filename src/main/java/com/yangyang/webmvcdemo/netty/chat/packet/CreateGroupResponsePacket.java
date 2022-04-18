package com.yangyang.webmvcdemo.netty.chat.packet;

import com.yangyang.webmvcdemo.netty.chat.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupResponsePacket extends Packet{
    private int groupId ;
    private boolean success;
    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
