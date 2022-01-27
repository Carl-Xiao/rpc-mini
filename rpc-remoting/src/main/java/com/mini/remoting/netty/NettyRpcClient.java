package com.mini.remoting.netty;

import com.mini.extention.ExtensionLoader;
import com.mini.factory.SingletonFactory;
import com.mini.model.MiniRpcRequest;
import com.mini.model.MiniRpcResponse;
import com.mini.protocol.MsgProtocol;
import com.mini.registry.model.ServiceMeta;
import com.mini.registry.service.RpcServiceHelper;
import com.mini.registry.service.ServiceRegistry;
import com.mini.remoting.AbstractClient;
import com.mini.remoting.netty.codec.MiniRpcDecoder;
import com.mini.remoting.netty.handler.NettyRpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * @author carl-xiao
 * @description
 **/
@Slf4j
public class NettyRpcClient implements AbstractClient {
    private final ChannelProvider channelProvider;
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;

    public NettyRpcClient() {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        // If no data is sent to the server within 15 seconds, a heartbeat request is sent
                        p.addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));
                        p.addLast(new MiniRpcDecoder());
                        p.addLast(new MiniRpcDecoder());
                        p.addLast(new NettyRpcClientHandler());
                    }
                });
        //注册中心
        this.channelProvider = SingletonFactory.getInstance(ChannelProvider.class);
    }

    @Override
    public CompletableFuture<MiniRpcResponse> sendRpcRequest(MsgProtocol<MiniRpcRequest> protocol, ServiceRegistry registryService) {
        CompletableFuture<MiniRpcResponse> resultFuture = new CompletableFuture<>();
        MiniRpcRequest request = protocol.getBody();
        Object[] params = request.getParams();
        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getServiceVersion());
        int invokerHashCode = params.length > 0 ? params[0].hashCode() : serviceKey.hashCode();
        try {
            ServiceMeta serviceMetadata = registryService.discovery(serviceKey, invokerHashCode);
            String addr = serviceMetadata.getServiceAddr();
            int port = serviceMetadata.getServicePort();
            InetSocketAddress socketAddress = new InetSocketAddress(addr, port);
            //发送请求
            Channel channel = getChannel(socketAddress);
            if (channel.isActive()) {
                channel.writeAndFlush(protocol).addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        log.info("client send message: [{}]", protocol.toString());
                    } else {
                        future.channel().close();
                        resultFuture.completeExceptionally(future.cause());
                        log.error("Send failed:", future.cause());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultFuture;
    }

    public Channel getChannel(InetSocketAddress inetSocketAddress) throws Exception {
        Channel channel = channelProvider.get(inetSocketAddress);
        if (channel == null) {
            channel = doConnect(inetSocketAddress);
            channelProvider.set(inetSocketAddress, channel);
        }
        return channel;
    }

    /**
     * 连接
     *
     * @param inetSocketAddress
     * @return
     */
    private Channel doConnect(InetSocketAddress inetSocketAddress) throws Exception, InterruptedException {
        CompletableFuture<Channel> completableFuture = new CompletableFuture<>();
        bootstrap.connect(inetSocketAddress).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("The client has connected [{}] successful!", inetSocketAddress.toString());
                completableFuture.complete(future.channel());
            } else {
                throw new IllegalStateException();
            }
        });
        return completableFuture.get();
    }


}
