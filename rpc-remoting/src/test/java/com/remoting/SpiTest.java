package com.remoting;

import com.mini.extention.ExtensionLoader;
import com.mini.registry.service.ServiceRegistry;
import com.mini.sc.Hello;
import org.junit.Test;

/**
 * @author carl-xiao
 * @description
 **/
public class SpiTest {

    @Test
    public void SpiTest() {
        Hello serviceRegistry = ExtensionLoader.getExtensionLoader(Hello.class).getExtension("zc");
        System.out.println(serviceRegistry.toString());
    }

    @Test
    public void SpiTest2() {
        ServiceRegistry serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("zk");
        System.out.println(serviceRegistry.toString());
    }


}
