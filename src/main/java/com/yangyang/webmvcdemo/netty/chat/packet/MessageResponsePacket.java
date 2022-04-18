package com.yangyang.webmvcdemo.netty.chat.packet;

import lombok.Data;

import static com.yangyang.webmvcdemo.netty.chat.command.Command.MESSAGE_RESPONSE;
@Data
public class MessageResponsePacket extends Packet{
    private String fromUserId;
    private String toUserId;
    private String message;
    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
