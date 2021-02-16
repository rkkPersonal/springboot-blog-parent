package org.xr.happy.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author Steven
 */
public class SmsSendListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(SmsSendListener.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        logger.info("借助spring容器收到消息：" + message);
    }
}