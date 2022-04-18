package com.yangyang.webmvcdemo.netty.chat.codec;

import com.yangyang.webmvcdemo.netty.chat.codec.Session;
import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("LoginSession");
}
