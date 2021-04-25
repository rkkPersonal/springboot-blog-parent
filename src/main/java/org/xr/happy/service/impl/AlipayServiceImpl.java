package org.xr.happy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.vo.TransferVo;
import org.xr.happy.service.AbstractPayService;

/**
 * @author Steven
 * <code>@Primary 如果一个接口有两个类实现，则 优先默认注入这个类</code>
 */
@Service("alipay")
//@Primary
public class AlipayServiceImpl extends AbstractPayService {

    private static final Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);

    @Override
    protected Result queryBalance(TransferVo vo) {

        return null;
    }

    @Override
    protected Result recharge(TransferVo orderVo) {
        logger.info("Alipay Recharge ");
        return null;
    }

    @Override
    protected Result withdraw(TransferVo orderVo) {
        logger.info("Alipay withdraw ");
        return null;
    }
}
