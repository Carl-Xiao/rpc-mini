package com.rpc.server.netty;

import com.mini.model.MiniRpcRequest;
import com.mini.model.MiniRpcResponse;
import com.mini.protocol.MsgHeader;
import com.mini.protocol.MsgProtocol;
import com.mini.protocol.MsgStatus;
import com.mini.protocol.MsgType;
import com.mini.registry.service.RpcServiceHelper;
import com.mini.spring.LocalService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.reflect.FastClass;

/**
 * @author carl-xiao
 * @description
 **/
@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<MsgProtocol<MiniRpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgProtocol<MiniRpcRequest> protocol) throws Exception {
        RpcRequestProcessor.submitRequest(() -> {
            MsgProtocol<MiniRpcResponse> resProtocol = new MsgProtocol<>();
            MiniRpcResponse response = new MiniRpcResponse();
            MsgHeader header = protocol.getHeader();
            header.setMsgType((byte) MsgType.RESPONSE.getType());
            try {
                Object result = handle(protocol.getBody());
                response.setData(result);
                header.setStatus((byte) MsgStatus.SUCCESS.getCode());
                resProtocol.setHeader(header);
                resProtocol.setBody(response);
            } catch (Throwable throwable) {
                header.setStatus((byte) MsgStatus.FAIL.getCode());
                response.setMessage(throwable.toString());
                log.error("process request {} error", header.getRequestId(), throwable);
            }
            ctx.writeAndFlush(resProtocol);
        });
    }

    private Object handle(MiniRpcRequest request) throws Throwable {
        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getServiceVersion());
        Object serviceBean = LocalService.loadService(serviceKey);
        if (serviceBean == null) {
            throw new RuntimeException(String.format("service not exist: %s:%s", request.getClassName(), request.getMethodName()));
        }
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParams();
        FastClass fastClass = FastClass.create(serviceClass);
        int methodIndex = fastClass.getIndex(methodName, parameterTypes);
        return fastClass.invoke(methodIndex, serviceBean, parameters);
    }


}
