package org.xr.happy.common.constant;

/**
 * @author Steven
 */
public class RedisKey {

    public static final  String TEST_CHANNEL_NAME = "sms_send";

    public static final  String ORDER_TIMEOUT = "order_timeout";

    public static final  String ORDER_TIMEOUT_CHANNEL_NAME = "__keyspace@0__:order_timeout";

    public static final String GEO_KEY = "user_geo";

    public static final String USER_INFO_KEY = "user_info_key";
    public static final String USER_BLOOM_FILTER = "user_bloom_filter";
}
