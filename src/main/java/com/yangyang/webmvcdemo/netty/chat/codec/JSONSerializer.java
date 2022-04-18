package com.yangyang.webmvcdemo.netty.chat.codec;

import com.alibaba.fastjson.JSON;

public class JSONSerializer implements Serializer{
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deserialize(Class<T> clz, byte[] bytes) {
        return JSON.parseObject(bytes,clz);
    }
}
