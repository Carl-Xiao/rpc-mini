package com.mini.spring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author carl-xiao
 * @description
 **/
public class LocalService {
    private static final Map<String, Object> rpcServiceMap = new ConcurrentHashMap<>();

    public static void storeService(String key, Object value) {
        rpcServiceMap.put(key, value);
    }

    public static Object loadService(String key) {
        if (rpcServiceMap.containsKey(key)) {
            return rpcServiceMap.get(key);
        }
        throw new RuntimeException("异常数据");
    }
}
