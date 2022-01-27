package com.mini.registry.service.impl;

import com.mini.registry.model.ServiceMeta;
import com.mini.registry.service.ServiceRegistry;

/**
 * @author carl-xiao
 * @description
 **/
public class SpIRegistry implements ServiceRegistry {
    @Override
    public void register(ServiceMeta serviceMeta) throws Exception {

    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) throws Exception {

    }

    @Override
    public ServiceMeta discovery(String serviceName, int hashcode) throws Exception {
        return null;
    }

    @Override
    public void destroy() throws Exception {

    }
}
