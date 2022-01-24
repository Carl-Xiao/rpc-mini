package com.mini.rpc.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author carl-xiao
 * @description
 **/
public enum RpcErrorMessageEnum {
    /**
     * 连接失败
     */
    CLIENT_CONNECT_SERVER_FAILURE(10001, "客户端连接服务端失败"),
    /**
     * 请求失败
     */
    SERVICE_INVOCATION_FAILURE(10002, "服务调用失败"),
    /**
     * 服务没有找到
     */
    SERVICE_CAN_NOT_BE_FOUND(10003, "没有找到指定的服务"),
    /**
     * 服务没有实现注册接口
     */
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE(10004, "注册的服务没有实现任何接口"),
    /**
     * 请求错误
     */
    REQUEST_NOT_MATCH_RESPONSE(10005, "返回结果错误！请求和返回的相应不匹配");
    @Getter
    private int code;
    @Getter
    private String message;

    RpcErrorMessageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
