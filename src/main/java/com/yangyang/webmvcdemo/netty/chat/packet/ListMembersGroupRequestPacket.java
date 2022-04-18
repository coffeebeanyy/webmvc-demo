package com.yangyang.webmvcdemo.netty.chat.packet;

import com.yangyang.webmvcdemo.netty.chat.command.Command;
import lombok.Data;

@Data
public class ListMembersGroupRequestPacket extends Packet{
    private int groupId;
    @Override
    public Byte getCommand() {
        return Command.LIST_MEMBER_GROUP_REQUEST;
    }
}
