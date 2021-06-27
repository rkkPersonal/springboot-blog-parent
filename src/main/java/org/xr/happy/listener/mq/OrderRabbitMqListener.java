package org.xr.happy.listener.mq;


import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.model.User;
import org.xr.happy.service.Server;

import java.util.Random;

@Component
public class OrderRabbitMqListener extends BaseMq<String> {


    private static final Logger logger = LoggerFactory.getLogger(OrderRabbitMqListener.class);

    @Autowired
    private Server server;

    @Autowired
    private UserMapper userMapper;

    @RabbitListener(queues = "xr-blog-love")
    @Override
    protected void process(Channel channel, String msg) {

        logger.info("我收到消息了:{}", msg);
        server.sendMessage(msg, "订单支付成功" + new Random().nextInt(10) + "元");

        // 模拟spring事务
        User user = new User();
        user.setId(Integer.parseInt(msg));
        User user1 = userMapper.selectOne(user);
        if (user1 == null) {
            logger.error("查询失败：userId :" + msg);
        } else {
            logger.info("查询成功： userId:" + msg);
        }
    }
}
