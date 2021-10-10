package org.xr.happy.listener.mq;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.xr.happy.common.enums.OrderStatus;
import org.xr.happy.mapper.OrderDetailMapper;
import org.xr.happy.mapper.ShoppingMapper;
import org.xr.happy.model.OrderDetail;
import org.xr.happy.model.Shopping;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Steven
 */
@Component
public class OrderSyncRabbitMqListener extends BaseMq<OrderDetail> {

    private static final Logger logger = LoggerFactory.getLogger(OrderSyncRabbitMqListener.class);

    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private ShoppingMapper shoppingMapper;

    /**
     * 修改订单状态并扣减库存
     *
     * @param channel
     * @param orderDetail
     */
    @RabbitListener(queues = "xr-blog-love")
    @Override
    protected void process(Channel channel, OrderDetail orderDetail) {
        Shopping shopping = new Shopping();
        shopping.setId(orderDetail.getShoppingId());
        Shopping shopInfo = shoppingMapper.selectOne(shopping);
        if (shopInfo.getShoppingCount().intValue() == 0) {
            logger.error("shopping storage is not enough:{}", shopInfo.getShoppingCount());
            return;
        }

        shoppingMapper.updateShoppingCount(orderDetail.getShoppingId(), orderDetail.getShoppingCount());

        orderDetail.setStatus(OrderStatus.CONFIRM.getStatus());
        orderDetailMapper.updateByPrimaryKeySelective(orderDetail);

    }

    @Override
    public String key() {
        return null;
    }

    @Override
    public Long retryTimes() {
        return null;
    }

    @Override
    public TimeUnit timeUnit() {
        return null;
    }

    @Override
    public Long time() {
        return null;
    }
}
