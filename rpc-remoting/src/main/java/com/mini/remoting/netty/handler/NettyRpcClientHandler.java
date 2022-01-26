package com.mini.remoting.netty.handler;

import com.mini.factory.SingletonFactory;
import com.mini.remoting.netty.NettyRpcClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author carl-xiao
 * @description
 **/
@Slf4j
public class NettyRpcClientHandler extends ChannelInboundHandlerAdapter {
    private final NettyRpcClient nettyRpcClient;

    public NettyRpcClientHandler() {
        this.nettyRpcClient = SingletonFactory.getInstance(NettyRpcClient.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            log.info("client receive msg: [{}]", msg);
        }catch (Exception e){
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
//            IdleState state = ((IdleStateEvent) evt).state();
//            if (state == IdleState.WRITER_IDLE) {
//                log.info("write idle happen [{}]", ctx.channel().remoteAddress());
//                Channel channel = nettyRpcClient.getChannel((InetSocketAddress) ctx.channel().remoteAddress());
//                RpcMessage rpcMessage = new RpcMessage();
//                rpcMessage.setCodec(SerializationTypeEnum.PROTOSTUFF.getCode());
//                rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
//                rpcMessage.setMessageType(RpcConstants.HEARTBEAT_REQUEST_TYPE);
//                rpcMessage.setData(RpcConstants.PING);
//                channel.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
    /**
     * Called when an exception occurs in processing a client message
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("client catch exception：", cause);
        cause.printStackTrace();
        ctx.close();
    }

}
