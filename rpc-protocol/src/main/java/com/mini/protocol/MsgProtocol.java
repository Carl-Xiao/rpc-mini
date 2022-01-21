package com.mini.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author carl-xiao
 * @description
 **/
@Data
public class MsgProtocol<T> implements Serializable {
    MsgHeader header;
    T body;
}
