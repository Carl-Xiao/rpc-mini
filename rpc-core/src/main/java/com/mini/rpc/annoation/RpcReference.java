package com.mini.rpc.annoation;

import java.lang.annotation.*;

/**
 * @author carl-xiao
 * @description
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {
    /**
     * 注册的版本号
     *
     * @return
     */
    String serviceVersion() default "1.0";
    /**
     * 超时时间
     *
     * @return
     */
    long timeout() default 5000;
}
