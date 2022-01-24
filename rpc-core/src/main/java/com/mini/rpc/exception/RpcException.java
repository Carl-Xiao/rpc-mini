package com.mini.rpc.exception;

import com.mini.rpc.enums.RpcErrorMessageEnum;

/**
 * @author carl-xiao
 * @description
 **/
public class RpcException extends RuntimeException {

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum) {
        super(rpcErrorMessageEnum.getMessage());
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }



}
