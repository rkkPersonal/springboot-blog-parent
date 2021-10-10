package org.xr.happy.controller.order;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.vo.OrderVo;
import org.xr.happy.service.order.OrderService;

import javax.annotation.Resource;

/**
 * @author Steven
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    public Result<OrderVo> createOrder(@RequestBody OrderVo orderVo) {
        return orderService.createOrder(orderVo);
    }
}
