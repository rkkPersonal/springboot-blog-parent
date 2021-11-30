package org.xr.happy.listener.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.xr.happy.model.User;

import java.util.concurrent.TimeUnit;

/**
 * @author Steven
 */
@Component
public class OrderTimeRabbitMqListener extends BaseMq<User> {


    private static final Logger logger = LoggerFactory.getLogger(OrderTimeRabbitMqListener.class);


    @RabbitListener(queues = "process.queue")
    @Override
    protected void process(Channel channel, User msg) {
        logger.info("开始消费延时队列:{}", JSON.toJSONString(msg));
        try {

            // TODO 需要处理的业务

        } catch (Exception e) {
            logger.error("延时队列业务处理异常:", e.getMessage());
            boolean maxIncrError = this.isMaxIncrError(this.key());
            if (maxIncrError) {
                return;
            } else {
                throw new RuntimeException("处理失败，请重试。。。。");
            }

        }

    }

    @Override
    public String key() {
        return "process.queue";
    }

    @Override
    public Long retryTimes() {
        return 3L;
    }

    @Override
    public TimeUnit timeUnit() {
        return TimeUnit.MINUTES;
    }

    @Override
    public Long time() {
        return 3L;
    }
}
