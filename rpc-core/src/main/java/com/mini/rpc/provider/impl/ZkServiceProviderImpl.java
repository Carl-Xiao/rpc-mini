package com.mini.rpc.provider.impl;

import com.mini.registry.model.ServiceMeta;
import com.mini.registry.service.ServiceRegistry;
import com.mini.rpc.config.RpcServiceConfig;
import com.mini.rpc.enums.RpcErrorMessageEnum;
import com.mini.rpc.exception.RpcException;
import com.mini.extention.ExtensionLoader;
import com.mini.rpc.provider.ServiceProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author carl-xiao
 * @description
 **/
@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {

    private final Map<String, Object> serviceMap;
    private final Set<String> registeredService;
    private final ServiceRegistry serviceRegistry;

    public ZkServiceProviderImpl() {
        serviceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
        //SPI机制扩展
        serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("zk");
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {
        String rpcServiceName = rpcServiceConfig.getRpcServiceName();
        if (registeredService.contains(rpcServiceName)) {
            return;
        }
        registeredService.add(rpcServiceName);
        serviceMap.put(rpcServiceName, rpcServiceConfig.getService());
        log.info("Add service: {} and interfaces:{}", rpcServiceName, rpcServiceConfig.getService().getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        if (null == service) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {
        try {
            //注册服务
            this.addService(rpcServiceConfig);
            //注册到zk
            ServiceMeta serviceMeta = new ServiceMeta();
            serviceMeta.setServiceAddr(rpcServiceConfig.getServerAddress());
            serviceMeta.setServicePort(rpcServiceConfig.getServerPort());
            serviceMeta.setServiceName(rpcServiceConfig.getServiceName());
            serviceMeta.setServiceVersion(rpcServiceConfig.getVersion());
            serviceRegistry.register(serviceMeta);
        } catch (Exception e) {
            log.error("occur exception when getHostAddress", e);
        }
    }
}
