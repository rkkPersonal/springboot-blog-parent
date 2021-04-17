package org.xr.happy.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTest {

    public static void main(String[] args) {


        Object o = newProxy(org.xr.happy.proxy.jdk.JdkInterface.class);

        org.xr.happy.proxy.jdk.JdkInterface jdkInterface = (JdkInterface) o;

        jdkInterface.process("宋心茹");


    }


    public static Object newProxy(Class<?> zClass) {

        ClassLoader classLoader = zClass.getClassLoader();

        Class<?>[] interfaces = new Class<?>[]{zClass};
        Object o = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


                Class<?> declaringClass = method.getDeclaringClass();
                if (Object.class.equals(declaringClass))

                    method.invoke(this, args);
                System.out.println("方法执行前");


                System.out.println("方法执行后");
                return null;


            }
        });
        return o;

    }


}
