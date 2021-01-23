package org.xr.happy.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.vo.TransferVo;
import org.xr.happy.service.PayServiceAbstract;

import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private Map<String, PayServiceAbstract> payMap;


    @PostMapping("/transfer")
    public Result transfer(@RequestBody TransferVo transferVo) {

        PayServiceAbstract payServiceAbstract = payMap.get(transferVo.getVerdor());

        Result transfer = payServiceAbstract.transfer(transferVo);

        return transfer;

    }
}
