package com.remoting;

import com.mini.extention.ExtensionLoader;
import com.mini.remoting.AbstractClient;
import org.junit.Test;

/**
 * @author carl-xiao
 * @description
 **/
public class SpiTest {

    @Test
    public void SpiTest() {
        AbstractClient serviceRegistry = ExtensionLoader.getExtensionLoader(AbstractClient.class).getExtension("netty");
        System.out.println(serviceRegistry.toString());
    }

}
