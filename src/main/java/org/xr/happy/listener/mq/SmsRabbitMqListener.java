package org.xr.happy.listener.mq;

import com.rabbitmq.client.Channel;
import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.xr.happy.common.enums.OrderStatus;
import org.xr.happy.mapper.OrderDetailMapper;
import org.xr.happy.mapper.ShoppingMapper;
import org.xr.happy.model.OrderDetail;
import org.xr.happy.model.Shopping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Steven
 */
@Component
public class SmsRabbitMqListener extends BaseMq<OrderDetail> {

    private static final Logger logger = LoggerFactory.getLogger(SmsRabbitMqListener.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 修改订单状态并扣减库存
     *
     * @param channel
     * @param orderDetail
     */
    @RabbitListener(queues = "sms-message-queue")
    @Override
    protected void process(Channel channel, OrderDetail sms) {

        logger.info("订单创建成功,开始发送短信:{}", sms);
        /*restTemplate.postForEntity("url", "", String.class, new HashMap<>());*/

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
