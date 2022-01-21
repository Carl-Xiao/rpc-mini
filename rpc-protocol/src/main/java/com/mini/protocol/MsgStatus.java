package com.mini.protocol;

import lombok.Getter;

/**
 * @author carl-xiao
 * @description 消息状态
 **/
public enum MsgStatus {
    /**
     * 成功
     */
    SUCCESS(0),
    /**
     * 失败
     */
    FAIL(1);
    @Getter
    private final int code;

    MsgStatus(int code) {
        this.code = code;
    }
}
