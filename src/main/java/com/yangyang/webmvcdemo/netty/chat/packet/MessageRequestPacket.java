package com.yangyang.webmvcdemo.netty.chat.packet;

import lombok.Data;

import static com.yangyang.webmvcdemo.netty.chat.command.Command.MESSAGE_REQUEST;
@Data
public class MessageRequestPacket extends Packet{
    private String fromUserId;
    private String toUserId;
    private String message;

    public MessageRequestPacket() {
    }

    public MessageRequestPacket(String fromUserId,String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
        this.fromUserId = fromUserId;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
