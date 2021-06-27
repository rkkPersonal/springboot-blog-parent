package org.xr.happy.listener.mq;

import com.rabbitmq.client.Channel;

/**
 * @author Steven
 */
public abstract class BaseMq<T> {

    /**
     * 业务处理
     *
     * @param channel
     * @param msg
     */
    protected abstract void process(Channel channel, T msg);

}
