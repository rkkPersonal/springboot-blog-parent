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

        Result result = this.queryBalance(transferVo);

        if (!result.getCode().equals(0)) {
            return Result.error(result.getMsg());
        }

        switch (transferVo.getTransferType()) {
            case 0:
                this.recharge(transferVo);
                break;
            case 1:
                this.withdraw(transferVo);
        }

        return Result.ok();
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
