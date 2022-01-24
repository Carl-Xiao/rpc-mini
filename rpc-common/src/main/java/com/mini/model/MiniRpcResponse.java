package com.mini.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author carl-xiao
 * @description
 **/
@Data
public class MiniRpcResponse implements Serializable {
    /**
     * 消息体
     */
    private Object data;
    /**
     * 异常错误消息
     */
    private String message;
}
