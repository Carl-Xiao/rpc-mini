package com.mini.protocol;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author carl-xiao
 * @description requestHolder
 **/
public class RpcRequestHolder {
    /**
     * requestId生成器
     */
    public final static AtomicLong REQUEST_ID_GEN = new AtomicLong(0);
}
