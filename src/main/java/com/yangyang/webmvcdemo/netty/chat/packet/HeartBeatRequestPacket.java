package com.yangyang.webmvcdemo.netty.chat.packet;

import com.yangyang.webmvcdemo.netty.chat.command.Command;

public class HeartBeatRequestPacket extends Packet{
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_REQUEST;
    }
}
