package org.xr.happy.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 手写内存缓存 并且可以设置过期时间
 */
@Component
public class ScheduledUtil {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledUtil.class);

    static Map<String, Object> map = new ConcurrentHashMap<>();

    public static void put(String key, String obj) {
        setSecond(key, obj, 5);
    }

    public static Map<String, Object> setSecond(String key, String value, int second) {
        map.put(key, value);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPoolExecutor.schedule(() -> {
            map.remove(key);
            logger.info(key + " " + "is" + " " + "expired");
        }, second, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.shutdown();
        return map;
    }

    public static Map<String, Object> setHours(String key, String value, int hours) {
        map.put(key, value);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPoolExecutor.schedule(() -> {
            map.remove(key);
            logger.info(key + " " + "is" + " " + "expired");
        }, hours, TimeUnit.HOURS);
        scheduledThreadPoolExecutor.shutdown();
        return map;
    }


    public static Map<String, Object> set(String key, String value, int time, TimeUnit timeUnit) {
        map.put(key, value);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPoolExecutor.schedule(() -> {
            map.remove(key);
            logger.info(key + " " + "is" + " " + "expired");
        }, time, timeUnit);
        scheduledThreadPoolExecutor.shutdown();
        return map;
    }

    public static void main(String[] args) throws Exception {

        Map<String, Object> put = ScheduledUtil.set("steven", "爱宋心茹", 4, TimeUnit.SECONDS);
        Map<String, Object> map = ScheduledUtil.set("peck", "爱james", 10, TimeUnit.SECONDS);

        logger.info(put.get("steven").toString());
        logger.info(map.get("peck").toString());
        Thread.sleep(4000);
    }
}
