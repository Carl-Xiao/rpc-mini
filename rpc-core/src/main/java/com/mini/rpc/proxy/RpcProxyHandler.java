package com.mini.rpc.proxy;

import com.mini.model.MiniRpcRequest;
import com.mini.protocol.*;
import com.mini.registry.service.ServiceRegistry;
import com.mini.serialization.SerializationTypeEnum;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author carl-xiao
 * @description 代理类
 **/
public class RpcProxyHandler implements InvocationHandler {

    private final String serviceVersion;
    private final long timeout;
    private final ServiceRegistry registryService;

    public RpcProxyHandler(String serviceVersion, long timeout, ServiceRegistry registryService) {
        this.serviceVersion = serviceVersion;
        this.timeout = timeout;
        this.registryService = registryService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MsgProtocol<MiniRpcRequest> protocol = new MsgProtocol<>();
        //header
        MsgHeader header = new MsgHeader();
        long requestId = RpcRequestHolder.REQUEST_ID_GEN.incrementAndGet();
        header.setMagic(ProtocolConstant.MAGIC);
        header.setVersion(ProtocolConstant.VERSION);
        header.setRequestId(requestId);
        header.setSerialization((byte) SerializationTypeEnum.HESSIAN.getType());
        header.setMsgType((byte) MsgType.REQUEST.getType());
        header.setStatus((byte) 0x1);
        protocol.setHeader(header);
        //body
        MiniRpcRequest request = new MiniRpcRequest();
        request.setServiceVersion(this.serviceVersion);
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        protocol.setBody(request);
        //send请求



        return null;
    }
}
