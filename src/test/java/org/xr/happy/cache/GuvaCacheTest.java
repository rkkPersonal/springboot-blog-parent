package org.xr.happy.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class GuvaCacheTest {

    public static void main(String[] args) {

        Cache cache = CacheBuilder.newBuilder().
                maximumSize(10)
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .removalListener(removalNotification -> {

                    System.out.println("key is removed :" + removalNotification.getKey());

                })
                .build();


        cache.put("steven", "666");
        Object u = cache.getIfPresent("steven");
        System.out.println("steven:" + u);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Object steven = cache.getIfPresent("steven");

        System.out.println("steven:" + steven);
    }
}
