package com.mini.spring;

import com.mini.extention.ExtensionLoader;
import com.mini.registry.model.ServiceMeta;
import com.mini.registry.service.ServiceRegistry;
import com.mini.remoting.AbstractClient;
import com.mini.remoting.proxy.RpcProxy;
import com.mini.rpc.annoation.RpcReference;
import com.mini.rpc.annoation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author carl-xiao
 * @description
 **/
@Slf4j
@Component
public class SpringBeanPostProcessor implements BeanPostProcessor {
    private String serverAddress;
    private Integer serverPort;
    private final AbstractClient rpcClient;
    private final ServiceRegistry serviceRegistry;

    public SpringBeanPostProcessor(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.rpcClient = ExtensionLoader.getExtensionLoader(AbstractClient.class).getExtension("netty");
        this.serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("zk");
    }
    /**
     * 注入前
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            log.info("[{}] is annotated with  [{}]", bean.getClass().getName(), RpcService.class.getCanonicalName());
            //rpc service
            RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
            Class<?> serviceInterface = rpcService.serviceInterface();
            String serviceName = serviceInterface.getName();
            String serviceVersion = rpcService.serviceVersion();

            ServiceMeta serviceMeta = new ServiceMeta();
            serviceMeta.setServiceAddr(serverAddress);
            serviceMeta.setServicePort(serverPort);
            //
            serviceMeta.setServiceName(serviceName);
            serviceMeta.setServiceVersion(serviceVersion);
            try {
                serviceRegistry.register(serviceMeta);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    /**
     * 注入后
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            RpcReference rpcReference = declaredField.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                String serviceVersion = rpcReference.serviceVersion();
                long timeout = rpcReference.timeout();
                //使用jdk代理bean
                RpcProxy rpcClientProxy = new RpcProxy(serviceVersion, timeout, serviceRegistry, rpcClient);
                Object clientProxy = rpcClientProxy.getProxy(declaredField.getType());
                declaredField.setAccessible(true);
                try {
                    declaredField.set(bean, clientProxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
