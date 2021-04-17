package org.xr.happy.cache;

import lombok.SneakyThrows;

public class CacheProviderTest {


    @SneakyThrows
    public static void main(String[] args) {

        CacheProvider cacheProvider = new CacheProvider();

        cacheProvider.put("hello", "I am here!");

        System.out.println("------------------不设置过期时间---------------------");
        System.out.println("key : " + cacheProvider.get("hello"));
        System.out.println("key : " + cacheProvider.remove("hello"));

        System.out.println("------------------设置过期时间---------------------");
        cacheProvider.put("Alipay", "failed!!!", 2000);

        System.out.println("Expired key :" + cacheProvider.get("Alipay"));

        Thread.sleep(3000);
        System.out.println("Expired key :" + cacheProvider.get("Alipay"));

    }
}
