package com.mini.serialization;


import com.mini.serialization.impl.HessionSerizlization;

/**
 * @author carl-xiao
 * @description 序列化工厂类
 **/
public class SerializationFactory {
    public static RpcSerialization getRpcSerialization(byte serializationType) {
        SerializationTypeEnum serializationTypeEnum = SerializationTypeEnum.findByType(serializationType);
        switch (serializationTypeEnum) {
            case HESSIAN:
                return new HessionSerizlization();
            default:
                throw new IllegalArgumentException("serialization type is illegal, " + serializationType);
        }
    }
}
