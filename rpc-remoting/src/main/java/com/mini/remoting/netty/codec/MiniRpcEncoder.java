package com.mini.remoting.netty.codec;

import com.mini.protocol.MsgHeader;
import com.mini.protocol.MsgProtocol;
import com.mini.serialization.RpcSerialization;
import com.mini.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author carl-xiao
 * @description rpc加密
 **/
public class MiniRpcEncoder extends MessageToByteEncoder<MsgProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MsgProtocol<Object> msg, ByteBuf byteBuf) throws Exception {
        MsgHeader header = msg.getHeader();
        //序列化
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getVersion());
        byteBuf.writeByte(header.getSerialization());
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(header.getRequestId());
        //序列化工厂
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(header.getSerialization());
        byte[] data = rpcSerialization.serialize(msg.getBody());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}
