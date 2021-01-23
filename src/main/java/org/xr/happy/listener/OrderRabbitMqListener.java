package org.xr.happy.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderRabbitMqListener {


    private static final Logger logger = LoggerFactory.getLogger(OrderRabbitMqListener.class);

    @RabbitListener(queues = "xr-blog-love")
    public void process(String msg ){

        logger.info("我收到消息了:{}",msg);
    }
}
