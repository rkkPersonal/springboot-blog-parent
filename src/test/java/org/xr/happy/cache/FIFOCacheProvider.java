package org.xr.happy.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FIFOCacheProvider {

    private static Map<String, CacheData> container = null;
    private static final ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(5);

    private static int MAX_CAPACITY_SIZE = 0;
    private final float LOAD_FACTOR = 0.75f;

    public FIFOCacheProvider(int maxSize) {
        MAX_CAPACITY_SIZE = maxSize;

        Double capacity = Math.ceil(maxSize / LOAD_FACTOR) + 1;

        container = new LinkedHashMap<String, CacheData>(capacity.intValue(), LOAD_FACTOR, false) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CacheData> eldest) {
                return size() > MAX_CAPACITY_SIZE;
            }
        };

    }


    public synchronized long size() {
        return container.size();
    }


    public synchronized <T> T get(String key) {
        return (T) container.get(key);
    }

    public synchronized <T> T remove(String key) {
        CacheData remove = container.remove(key);
        return remove == null ? null : (T) remove;
    }


    public synchronized void put(String key, Object value) {
        this.put(key, value, -1);

    }


    public synchronized void put(String key, Object value, long expire) {

        if (expire > 0) {
            schedule.schedule(() -> {
                synchronized (this) {
                    container.remove(key);
                }

            }, expire, TimeUnit.MICROSECONDS);
            container.put(key, new CacheData(value, expire));
        } else {
            container.put(key, new CacheData(value, -1));
        }

    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    protected class CacheData {

        private Object data;

        private long expire;


    }

    @Override
    public String toString() {

        StringBuffer stringBuffer = new StringBuffer();


        for (Map.Entry<String, CacheData> result : container.entrySet()) {
            String key = result.getKey();
            Object value = result.getValue().data;
            stringBuffer.append(key).append("=").append(value).append("\n");
        }

        return stringBuffer.toString();
    }

}

