package com.mini.registry.service;

/**
 * @author carl-xiao
 * @description
 **/
public class RpcServiceHelper {
    public static String buildServiceKey(String serviceName, String serviceVersion) {
        return String.join("#", serviceName, serviceVersion);
    }
}
