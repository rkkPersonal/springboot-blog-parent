package org.xr.happy.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * 除了可以用 keyspance 事件，
 *
 * @author Steven
 */
public class OrderTimeoutListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderTimeoutListener.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        logger.info("=========订单监听开始：===========" + message);

        // todo 如果订单超时 ，则进行 修改订单状态。。。
        if ("expired".equals(message.toString())) {
            logger.info("订单已经超时，请重新下单。。。。");
        }
    }
}
