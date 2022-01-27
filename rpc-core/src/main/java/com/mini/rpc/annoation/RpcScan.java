package com.mini.rpc.annoation;

import java.lang.annotation.*;

/**
 * @author carl-xiao
 * @description
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcScan {
    String[] basePackage();
}
