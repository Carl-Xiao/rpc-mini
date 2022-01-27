package com.mini.spring.config;

import com.mini.spring.CustomScannerRegistrar;
import com.mini.spring.SpringBeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author carl-xiao
 * @description
 **/
@Configuration
@EnableConfigurationProperties(RpcProperties.class)
public class RpcAutoConfiguration {

    @Resource
    RpcProperties rpcProperties;

    @Bean
    CustomScannerRegistrar customScannerRegistrar() {
        return new CustomScannerRegistrar();
    }

    @Bean
    SpringBeanPostProcessor springBeanPostProcessor() {
        return new SpringBeanPostProcessor(rpcProperties.getRegistryAddr(), rpcProperties.getServicePort());
    }
}
