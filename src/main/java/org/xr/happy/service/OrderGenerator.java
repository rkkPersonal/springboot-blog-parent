package org.xr.happy.service;

import org.xr.happy.common.dto.Result;
import org.xr.happy.common.vo.OrderVo;

/**
 * @author Steven
 */
public interface OrderGenerator {

    /**
     * generator order id
     *
     * @return
     */
    String generatorOrderId();

     Result<OrderVo> createOrder(OrderVo orderVo);
}
