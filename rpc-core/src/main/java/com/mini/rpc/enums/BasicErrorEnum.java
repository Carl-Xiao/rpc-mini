package com.mini.rpc.enums;

/**
 * @author carl-xiao
 * @description
 **/
public enum BasicErrorEnum {
    ;
    private int code;
    private int message;

    BasicErrorEnum(int code, int message) {
        this.code = code;
        this.message = message;
    }
}
