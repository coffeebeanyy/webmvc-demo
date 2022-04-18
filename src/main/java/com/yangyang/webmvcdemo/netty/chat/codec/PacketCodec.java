package com.yangyang.webmvcdemo.netty.chat.codec;

import com.yangyang.webmvcdemo.netty.chat.command.Command;
import com.yangyang.webmvcdemo.netty.chat.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PacketCodec {
    public static final int MAGIC_NUMBER = 0x12345678;
    private static Map<Byte,Class<? extends Packet>> map = new HashMap<>();
    static {
        map.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        map.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        map.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        map.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        map.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        map.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        map.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        map.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        map.put(Command.LIST_MEMBER_GROUP_REQUEST, ListMembersGroupRequestPacket.class);
        map.put(Command.LIST_MEMBER_GROUP_RESPONSE, ListMembersGroupResponsePacket.class);
        map.put(Command.JOIN_GROUP_REQUEST,JoinGroupRequestPacket.class);
        map.put(Command.JOIN_GROUP_RESPONSE,JoinGroupResponsePacket.class);
        map.put(Command.GROUP_MESSAGE_REQUEST,GroupMessageRequestPacket.class);
        map.put(Command.GROUP_MESSAGE_RESPONSE,GroupMessageResponsePacket.class);
        map.put(Command.HEART_BEAT_REQUEST,HeartBeatRequestPacket.class);
        map.put(Command.HEART_BEAT_RESPONSE,HeartBeatResponsePacket.class);
    }
    public static final PacketCodec INSTANCE = new PacketCodec();

    public void encode(ByteBuf buf,Packet packet){
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //encode
        buf.writeInt(MAGIC_NUMBER);
        buf.writeByte(packet.getVersion());
        buf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buf.writeByte(packet.getCommand());
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }
    public ByteBuf encode(ByteBufAllocator allocator,Packet packet){
        ByteBuf buf = allocator == null ? ByteBufAllocator.DEFAULT.ioBuffer() : allocator.ioBuffer();

        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //encode
        buf.writeInt(MAGIC_NUMBER);
        buf.writeByte(packet.getVersion());
        buf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buf.writeByte(packet.getCommand());
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        return buf;
    }

    public Packet decode(ByteBuf buf){
        //跳过魔术 and version
        buf.skipBytes(4);
        buf.skipBytes(1);

        byte serializerAlgorithm = buf.readByte();
        byte command = buf.readByte();
        int length = buf.readInt();
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        Serializer serializer = getSerializer(serializerAlgorithm);
        Class<? extends Packet> requestType = getRequestType(command);
        if(requestType != null && serializer != null){
            return serializer.deserialize(requestType,bytes);
        }
        log.error("serializer is null or requestType is null");
        return null;
    }

    private Serializer getSerializer(int serializerAlgorithm){
        if(serializerAlgorithm == 1){
            return new JSONSerializer();
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command){
        return map.get(command);
    }
}
