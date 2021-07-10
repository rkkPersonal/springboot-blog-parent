package org.xr.happy.loader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoader {

    public static void main(String[] args) throws Exception {


        File file = new File("E:\\my_demo_test\\test");

        URL url = file.toURI().toURL();

        URLClassLoader parentClassLoader = new URLClassLoader(new URL[]{url});

        while (true) {

            URLClassLoader urlClassLoader = new URLClassLoader(parentClassLoader.getURLs());

            Class<?> helloService = urlClassLoader.loadClass("HelloService");

            Object o = helloService.newInstance();
            Method[] methods = helloService.getMethods();
            Object invoke = methods[0].invoke(o);
            Thread.sleep(1000);

        }
    }

}
