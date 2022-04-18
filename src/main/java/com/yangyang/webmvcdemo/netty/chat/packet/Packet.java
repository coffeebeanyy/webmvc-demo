package com.yangyang.webmvcdemo.netty.chat.packet;

import lombok.Data;

@Data
public abstract class Packet {

    private Byte version = 1;

    private boolean isSuccess = false;

    public abstract Byte getCommand();
}
