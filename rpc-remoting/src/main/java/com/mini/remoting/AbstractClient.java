package com.mini.remoting;

import com.mini.extention.SPI;
import com.mini.model.MiniRpcRequest;
import com.mini.protocol.MsgProtocol;
import com.mini.registry.service.ServiceRegistry;

/**
 * @author carl-xiao
 * @description
 **/
@SPI
public interface AbstractClient {
    /**
     * send rpc request to server and get result
     *
     * @param protocol        协议
     * @param registryService 注册服务
     * @return data from server
     */
    Object sendRpcRequest(MsgProtocol<MiniRpcRequest> protocol, ServiceRegistry registryService);
}
