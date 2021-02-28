package org.xr.happy.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xr.happy.service.Server;

import java.util.Random;

@Component
public class OrderRabbitMqListener {


    private static final Logger logger = LoggerFactory.getLogger(OrderRabbitMqListener.class);

    @Autowired
    private Server server;

    @RabbitListener(queues = "xr-blog-love")
    public void process(String msg ){

        logger.info("我收到消息了:{}",msg);
        server.sendMessage(msg,"订单支付成功"+new Random().nextInt(10)+"元");
    }
}
