package org.xr.happy.service;

import org.springframework.stereotype.Component;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.vo.TransferVo;

/**
 * @author Steven
 */
@Component
public abstract class AbstractPayService implements PayService {

    @Override
    public Result transfer(TransferVo transferVo) {

        this.queryBalance(transferVo);

        if (transferVo.getTransferType() == 0) {

            this.recharge(transferVo);
        }

        this.withdraw(transferVo);

        return null;
    }

    /**
     * 余额查询
     *
     * @param vo
     * @return
     */
    protected abstract Result queryBalance(TransferVo vo);

    /**
     * 充值
     *
     * @param orderVo
     * @return
     */
    protected abstract Result recharge(TransferVo orderVo);

    /**
     * 提现
     *
     * @param orderVo
     * @return
     */
    protected abstract Result withdraw(TransferVo orderVo);
}
