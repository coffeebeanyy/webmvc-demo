package com.yangyang.webmvcdemo.netty.chat.codec;

import com.yangyang.webmvcdemo.netty.chat.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        log.info("PacketEncoder ...");
        PacketCodec.INSTANCE.encode(out,msg);
    }
}
