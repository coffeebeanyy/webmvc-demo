package com.yangyang.webmvcdemo.netty.chat.packet;

import lombok.Data;

import static com.yangyang.webmvcdemo.netty.chat.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet{
    private boolean isSuccess = false;
    private String message;
    private String userId;
    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
