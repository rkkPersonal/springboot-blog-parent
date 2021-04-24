package org.xr.happy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.xr.happy.service.OrderGenerator;

import java.time.LocalDateTime;

import static org.xr.happy.common.constant.RedisKey.ORDER_ID;

/**
 * @author Steven
 */
@Service
public class OrderServiceImpl implements OrderGenerator {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String generatorOrderId() {
        Long increment = redisTemplate.opsForValue().increment(ORDER_ID, -1);
        String orderId = generator() + "-" + increment;
        logger.info("订单号号是:{}",orderId);
        return orderId;
    }

    private String generator() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int monthValue = now.getMonthValue();
        int dayOfMonth = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        String id = year + monthValue + dayOfMonth + hour + minute + second + "";
        return id;
    }
}
