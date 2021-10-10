package org.xr.happy.common.enums;

import lombok.Getter;

/**
 * @author Steven
 */

@Getter
public enum OrderStatus {
   CREATED(0,"下单成功,等待确认"),
    CONFIRM(1,"已确认,未支付"),
    PAYED(2,"已确认，并支付成功"),
    ERROR(3,"订单异常"),
    ;


    private Integer status;

    private String descr;

    OrderStatus(Integer status, String descr) {
        this.status = status;
        this.descr = descr;
    }
}
