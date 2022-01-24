package com.mini.rpc.provider;

import com.mini.rpc.config.RpcServiceConfig;

/**
 * @author carl-xiao
 * @description 内存存储service
 **/
public interface ServiceProvider {
    /**
     * @param rpcServiceConfig
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * @param rpcServiceName
     * @return
     */
    Object getService(String rpcServiceName);

    /**
     * 发布service
     *
     * @param rpcServiceConfig
     */
    void publishService(RpcServiceConfig rpcServiceConfig);
}
