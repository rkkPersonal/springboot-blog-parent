package org.xr.happy.cache;

import java.util.UUID;

public class FIFOCacheProviderTest {

    public static void main(String[] args) {

        FIFOCacheProvider fifoCacheProvider = new FIFOCacheProvider(5);

        for (int i = 0; i < 10; i++) {
            fifoCacheProvider.put("id=" + i, UUID.randomUUID().toString());
        }

        System.out.println("缓存大小：" + fifoCacheProvider.size());
        System.out.println("缓存数量：" + fifoCacheProvider.toString());
    }
}
