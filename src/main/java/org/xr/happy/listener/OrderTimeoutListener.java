package org.xr.happy.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class OrderTimeoutListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderTimeoutListener.class);


    @Override
    public void onMessage(Message message, byte[] pattern) {
        logger.info("支付订单超时："+message);
    }
}
