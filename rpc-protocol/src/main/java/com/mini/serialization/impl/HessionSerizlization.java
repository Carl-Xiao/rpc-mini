package com.mini.serialization.impl;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;
import com.mini.serialization.RpcSerialization;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author carl-xiao
 * @description hession序列化
 **/
public class HessionSerizlization implements RpcSerialization {
    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        if (null == obj) {
            throw new NullPointerException();
        }
        byte[] results;
        HessianSerializerOutput hessianOutput;
        try (ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream()) {
            hessianOutput = new HessianSerializerOutput(byteOutPut);
            hessianOutput.writeObject(obj);
            hessianOutput.flush();
            results = byteOutPut.toByteArray();
        } catch (Exception e) {
            throw new SerializationException(e);
        }
        return results;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        if (data == null) {
            throw new NullPointerException();
        }
        T result;
        try (ByteArrayInputStream is = new ByteArrayInputStream(data)) {
            HessianSerializerInput hessianInput = new HessianSerializerInput(is);
            result = (T) hessianInput.readObject(clz);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
        return result;
    }
}
