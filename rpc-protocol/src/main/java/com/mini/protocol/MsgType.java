package com.mini.protocol;

import lombok.Getter;

/**
 * @author carl-xiao
 * @description
 **/
public enum MsgType {
    /**
     * 请求
     */
    REQUEST(1),
    /**
     * 响应
     */
    RESPONSE(2),
    /**
     * 心跳检测
     */
    HEARTBEAT(3);
    @Getter
    private final int type;

    MsgType(int type) {
        this.type = type;
    }

    /**
     * 获取消息类型
     *
     * @param type
     * @return
     */
    public static MsgType findByType(int type) {
        for (MsgType msgType : MsgType.values()) {
            if (msgType.getType() == type) {
                return msgType;
            }
        }
        return null;
    }

}
