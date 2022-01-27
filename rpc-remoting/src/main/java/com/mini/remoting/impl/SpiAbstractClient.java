package com.mini.remoting.impl;

import com.mini.model.MiniRpcRequest;
import com.mini.model.MiniRpcResponse;
import com.mini.protocol.MsgProtocol;
import com.mini.registry.service.ServiceRegistry;
import com.mini.remoting.AbstractClient;

import java.util.concurrent.CompletableFuture;

/**
 * @author carl-xiao
 * @description
 **/
public class SpiAbstractClient implements AbstractClient {
    @Override
    public CompletableFuture<MiniRpcResponse> sendRpcRequest(MsgProtocol<MiniRpcRequest> protocol, ServiceRegistry registryService) {
        return null;
    }
}
