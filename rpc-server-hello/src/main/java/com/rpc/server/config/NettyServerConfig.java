package com.rpc.server.config;

import com.rpc.server.netty.NettyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author carl-xiao
 * @description
 **/
@Configuration
public class NettyServerConfig {

    @Value("${netty.address}")
    public String address;
    @Value("${netty.port}")
    public int port;

    @Bean
    public NettyServer init() {
        return new NettyServer(address, port);
    }
}
