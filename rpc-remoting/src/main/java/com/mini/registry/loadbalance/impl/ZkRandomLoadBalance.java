package com.mini.registry.loadbalance.impl;

import com.mini.registry.loadbalance.LoadBalance;
import com.mini.registry.model.ServiceMeta;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Random;

/**
 * @author carl-xiao
 * @description 随机hash路由
 **/
public class ZkRandomLoadBalance implements LoadBalance<ServiceInstance<ServiceMeta>> {
    @Override
    public ServiceInstance<ServiceMeta> select(List<ServiceInstance<ServiceMeta>> servers, int hashcode) {
        Random random = new Random();
        int size = servers.size() - 1;
        int index = random.nextInt(size);
        return servers.get(index);
    }
}
