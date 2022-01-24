package com.mini.remoting.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author carl-xiao
 * @description 远程调用请求类
 **/
@Data
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    /**
     * 请求ID
     */
    private String requestId;
    /**
     * 接口昵称
     */
    private String interfaceName;
    /**
     * 方法昵称
     */
    private String methodName;
    /**
     * 参数
     */
    private Object[] parameters;
    /**
     * 参数类型
     */
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    public String getRpcServiceName() {
        return this.getInterfaceName() + this.getGroup() + this.getVersion();
    }
}
