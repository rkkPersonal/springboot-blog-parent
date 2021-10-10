package org.xr.happy.service.order;

import org.xr.happy.common.dto.Result;
import org.xr.happy.common.vo.OrderVo;

/**
 * @author Steven
 */
public interface OrderService {

    public Result<OrderVo> createOrder(OrderVo orderVo);
}
