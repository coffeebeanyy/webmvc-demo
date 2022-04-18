package com.yangyang.webmvcdemo.netty.chat.codec;

import lombok.Data;

@Data
public class Session {
    private String userId;
    private String userName;
}
