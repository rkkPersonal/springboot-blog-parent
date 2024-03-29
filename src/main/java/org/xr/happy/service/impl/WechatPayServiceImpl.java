package org.xr.happy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.vo.TransferVo;
import org.xr.happy.service.AbstractPayService;

/**
 * @author Steven
 */
@Service("wechat")
public class WechatPayServiceImpl extends AbstractPayService {

    private static final Logger logger = LoggerFactory.getLogger(WechatPayServiceImpl.class);

    @Override
    protected Result queryBalance(TransferVo vo) {
        return null;
    }

    @Override
    protected Result recharge(TransferVo orderVo) {
        logger.info("Wechat Recharge ");
        return null;
    }

    @Override
    protected Result withdraw(TransferVo orderVo) {
        logger.info("Wechat Recharge ");
        return null;
    }
}
