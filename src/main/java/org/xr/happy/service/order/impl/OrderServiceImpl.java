package org.xr.happy.service.order.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.enums.OrderStatus;
import org.xr.happy.common.vo.OrderVo;
import org.xr.happy.service.order.OrderService;
import org.xr.happy.mapper.OrderDetailMapper;
import org.xr.happy.mapper.ShoppingMapper;
import org.xr.happy.model.OrderDetail;
import org.xr.happy.model.Shopping;

import javax.annotation.Resource;

/**
 * @author Steven
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private ShoppingMapper shoppingMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;


    @Override
    public Result<OrderVo> createOrder(OrderVo orderVo) {
        Shopping shopping = new Shopping();
        shopping.setId(orderVo.getShoppingId());
        Shopping shopInfo = shoppingMapper.selectOne(shopping);
        if (shopInfo.getShoppingCount().intValue()==0){
            return Result.error("该商品已经售完了");
        }

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderName(orderVo.getShoppingName());
        orderDetail.setShoppingId(orderVo.getShoppingId());
        orderDetail.setStatus(OrderStatus.CREATED.getStatus());
        orderDetail.setRemark("需要混合颜色款式的");
        orderDetailMapper.insert(orderDetail);
        rabbitTemplate.convertAndSend("xr-blog-love", orderDetail);
        rabbitTemplate.convertAndSend("",orderDetail);
        return Result.ok();
    }
}
