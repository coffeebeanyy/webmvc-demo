package com.yangyang.webmvcdemo.netty.chat.packet;

import com.yangyang.webmvcdemo.netty.chat.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class ListMembersGroupResponsePacket extends Packet{
    private int groupId;
    private List<String> nameList;

    @Override
    public Byte getCommand() {
        return Command.LIST_MEMBER_GROUP_RESPONSE;
    }
}
