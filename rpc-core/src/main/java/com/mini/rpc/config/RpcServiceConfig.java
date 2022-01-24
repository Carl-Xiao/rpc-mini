package com.mini.rpc.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author carl-xiao
 * @description
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcServiceConfig {
    /**
     * service version
     */
    private String version = "";
    /**
     * when the interface has multiple implementation classes, distinguish by group
     */
    private String group = "";
    /**
     * target service
     */
    private Object service;
    /**
     * 注册地址
     */
    private String serverAddress;
    /**
     * 注册端口
     */
    private int serverPort;

    public String getRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }

    public String getServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }
}
