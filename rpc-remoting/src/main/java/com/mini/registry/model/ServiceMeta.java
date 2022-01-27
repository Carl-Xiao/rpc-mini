package com.mini.registry.model;

import lombok.Data;

/**
 * @author carl-xiao
 * @description
 **/
@Data
public class ServiceMeta {
    /**
     * 服务注册名
     */
    private String serviceName;
    /**
     * 服务注册版本
     */
    private String serviceVersion;
    /**
     * 服务注册地址
     */
    private String serviceAddr;
    /**
     * 服务注册端口号
     */
    private int servicePort;
}
