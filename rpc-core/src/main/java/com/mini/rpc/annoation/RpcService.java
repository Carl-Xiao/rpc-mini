package com.mini.rpc.annoation;

import java.lang.annotation.*;

/**
 * @author carl-xiao
 * @description
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {
    /**
     * 接口类
     *
     * @return
     */
    Class<?> serviceInterface() default Object.class;


    String serviceVersion() default "1.0";
}
