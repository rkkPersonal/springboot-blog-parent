package org.xr.happy.service;

import org.xr.happy.common.dto.Result;
import org.xr.happy.common.vo.TransferVo;

/**
 * @author Steven
 */
public interface PayService {

    public Result transfer(TransferVo transferVo);
}
