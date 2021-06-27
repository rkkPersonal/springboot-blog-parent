package org.xr.happy.listener.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.xr.happy.config.RabbitMqConfig;
import org.xr.happy.model.User;

/**
 * @author Steven
 */
@Component
public class OrderTimeRabbitMqListener extends BaseMq<User> {

    private static final Logger logger = LoggerFactory.getLogger(OrderTimeRabbitMqListener.class);



    @RabbitListener(queues = RabbitMqConfig.PROCESS_QUEUE)
    @Override
    protected void process(Channel channel, User msg) {
        logger.info("开始消费延时队列:{}", JSON.toJSONString(msg));
    }
}
