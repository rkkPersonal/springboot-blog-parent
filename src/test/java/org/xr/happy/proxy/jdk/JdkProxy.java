package org.xr.happy.proxy.jdk;

public class JdkProxy implements JdkInterface {


    @Override
    public String process(String param) {

        System.out.println("steven 666="+param);

        return "JdkProxy !";
    }



}
