package com.yangyang.webmvcdemo;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class HelloWorld {
    public static void main(String[] args) {
        Map<String,String> map = new IdentityHashMap<>();
        String s1 = "a";
        String s2 = "a";
        String s3 = "a";
        map.put(s1,"a1");
        map.put(s2,"b2");
        map.put(s3,"c3");

        System.out.println(s1.hashCode() +" - "+s2.hashCode()+" - "+s3.hashCode());
        System.out.println(map.size());
        System.out.println(map.get(s1));

        Map<String,String> hashMap = new HashMap<>();
        hashMap.put(s1,"a1");
        hashMap.put(s2,"b1");
        hashMap.put(s3,"c1");
        System.out.println(hashMap);
    }
}
