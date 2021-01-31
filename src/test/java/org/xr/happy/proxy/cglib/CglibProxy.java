package org.xr.happy.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

public class CglibProxy {


    public String process(){
        System.out.println("steven"+new Date());
        return "hello";
    }

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(CglibProxy.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                System.out.println("before executor");

                Object o1 = methodProxy.invokeSuper(o, objects);

                System.out.println("cglib method invoke result:"+o1);
                System.out.println("after method ");

                return o1;
            }
        });

        CglibProxy proxy = (CglibProxy)enhancer.create();

        proxy.process();

    }
}
