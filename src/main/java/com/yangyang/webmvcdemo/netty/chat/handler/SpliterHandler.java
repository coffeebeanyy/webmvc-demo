package com.yangyang.webmvcdemo.netty.chat.handler;

import com.yangyang.webmvcdemo.netty.chat.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class SpliterHandler extends LengthFieldBasedFrameDecoder {
    private static final int FIELD_OFFSET = 7;
    private static final int FIELD_LENGTH = 4;
    public SpliterHandler(){
        super(Integer.MAX_VALUE,FIELD_OFFSET,FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //屏蔽非本协议的客户端
        if(in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER){
            System.out.println("非本协议的数据包...直接关闭客户端...");
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
