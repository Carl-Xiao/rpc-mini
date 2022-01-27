package com.rpc.server.facade;

import com.mini.facade.HelloFacade;
import com.mini.rpc.annoation.RpcService;

/**
 * @author carl-xiao
 * @description
 **/
@RpcService(serviceInterface = HelloFacade.class, serviceVersion = "1.0.0")
public class HelloServiceImpl implements HelloFacade {
    @Override
    public String hello(String name) {
        return name + " say:hello";
    }
}
