package org.xr.happy.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * redis distribute lock is for demo ,Just for simple practice and study the mindset
 * but can't user to prod
 *
 * @author Steven
 */
public class RedisLock {
    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private static final Long TIMEOUT = 5L;
    static Long currentTime = System.currentTimeMillis();

    private RedisTemplate redisTemplate;

    public RedisLock(RedisTemplate redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    public boolean lock(String key) {


        if (StringUtils.isNotBlank(key)) {
            boolean setNx = redisTemplate.opsForValue().setIfAbsent(key, currentTime, TIMEOUT, TimeUnit.SECONDS);
            if (setNx) {
                logger.info("加锁成功");
                return true;
            } else {

                String oldValue = String.valueOf(redisTemplate.opsForValue().get(key));
                Long oldTime = Long.parseLong(oldValue) + TIMEOUT;
                if (currentTime.longValue() >= oldTime.longValue()) {
                    String andSet = String.valueOf(redisTemplate.opsForValue().getAndSet(key, currentTime));
                    if (StringUtils.isNotBlank(andSet)) {
                        if (oldValue.equals(andSet)) {
                            logger.info("第二次加锁成功");
                            return true;
                        }
                    }

                }

            }
        }

        logger.info("加锁失败");
        return false;

    }


    public void unLock(String key) {
        redisTemplate.delete(key);
        logger.info("锁释放成功");
    }


    public static void main(String[] args) {

    }
}
