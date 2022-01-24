package com.registry;

import com.mini.extention.ExtensionLoader;
import com.mini.registry.service.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author carl-xiao
 * @description
 **/
@Slf4j
public class SpiTest {

    @Test
    public void SpiTest() {
        ServiceRegistry serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("zk");
        System.out.println(serviceRegistry.toString());
    }
}
