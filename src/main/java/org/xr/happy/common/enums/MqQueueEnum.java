package org.xr.happy.common.enums;

import lombok.Getter;

/**
 * @author Steven
 */

@Getter
public enum MqQueueEnum {
    ORDER_CREATED_QUEUE("order-create-queue","下单成功") ,
    SMS_MESSAGE_QUEUE("sms_message_queue","下单成功"),
    XR_BLOG_LOVE_QUEUE("xr-blog-love-queue",""),
    REDUCE_STORAGE_QUEUE("reduce-storage-queue","减库存");

    private String queue;

    private String descr;

    MqQueueEnum(String queue, String descr) {
        this.queue = queue;
        this.descr = descr;
    }
}
