package com.yangyang.webmvcdemo.netty.chat.packet;

import lombok.Data;

import static com.yangyang.webmvcdemo.netty.chat.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet{
    private String userId;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
