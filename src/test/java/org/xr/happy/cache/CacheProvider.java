package org.xr.happy.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CacheProvider {

    private static final Map<String, CacheData> container = new ConcurrentHashMap<>();
    private static final ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(5);


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

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class CacheData {

    private Object data;

    private long expire;


}