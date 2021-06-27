package org.xr.happy.listener.mq;

import com.rabbitmq.client.Channel;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author Steven
 */
public abstract class BaseMq<T> {

    private RedisTemplate redisTemplate = new RedisTemplate();

    /**
     * 业务处理
     *
     * @param channel
     * @param msg
     */
    protected abstract void process(Channel channel, T msg);


    /**
     * @return
     */
    public abstract String key();

    /**
     * 重试次数
     *
     * @return
     */
    public abstract Long retryTimes();


    /**
     * 过期时间
     *
     * @return
     */
    public abstract TimeUnit timeUnit();

    /**
     * @return
     */
    public abstract Long time();


    /**
     * 最大重试次数
     *
     * @param key
     * @return
     */
    public boolean isMaxIncrError(String key) {

        String incrKey = this.key();
        if (!key.equals(incrKey)) {
            return false;
        }

        TimeUnit timeUnit = this.timeUnit();
        Long time = this.time();
        Long increment = redisTemplate.opsForValue().increment(incrKey, timeUnit.toMillis(time));
        Long retryTimes = this.retryTimes();
        if (increment > retryTimes) {
            return true;
        }

        return false;
    }

}
