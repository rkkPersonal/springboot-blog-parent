package org.xr.happy.controller.notify;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xr.happy.common.enums.PayStatusEnum;

/**
 * @author Steven
 */
@RequestMapping("/notify")
public class NotifyController {


    @PostMapping("/alipay")
    public String asyncNotify() {

        return PayStatusEnum.SUCCESS.name();
    }
}
