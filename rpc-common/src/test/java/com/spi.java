package com;

import com.mini.extention.ExtensionLoader;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author carl-xiao
 * @description
 **/
public class spi {
    private static final String SERVICE_DIRECTORY = "META-INF/extensions/";

    @Test
    public void SpiTest() {
        String fileName = SERVICE_DIRECTORY + "com.mini.sc.Hello";
        try {
            Enumeration<URL> urls;
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
            urls = classLoader.getResources(fileName);
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL resourceUrl = urls.nextElement();
                    System.out.println(resourceUrl);
//                    loadResource(extensionClasses, classLoader, resourceUrl);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("end");
//        Hello serviceRegistry = ExtensionLoader.getExtensionLoader(Hello.class).getExtension("zc");
//        System.out.println(serviceRegistry.toString());
    }



}
