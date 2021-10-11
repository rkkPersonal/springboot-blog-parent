package org.xr.happy.config;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Configuration;
import org.xr.happy.common.enums.MqEnum;
import org.xr.happy.service.order.impl.OrderServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Steven
 */
@Configuration
public class RabbitmqInitConfig implements SmartInitializingSingleton {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Resource
    private AmqpAdmin amqpAdmin;

    @Resource
    private RabbitmqProperties rabbitmqProperties;

    @Override
    public void afterSingletonsInstantiated() {
        initRabbitmq();
    }

    private void initRabbitmq() {

        List<String> queueNames = rabbitmqProperties.getQueueNames();
        String exchangeName = rabbitmqProperties.getExchange();
        String routingKey = rabbitmqProperties.getRoutingKey();
        String type = rabbitmqProperties.getType();

        if (CollectionUtils.isEmpty(queueNames)) {
            logger.warn("配置文件中不含有组件信息，不进行注册！");
            return;
        }

        //注册组件信息
        for (String queueName : queueNames) {
            //获取队列
            Queue queue = new Queue(queueName, true, false, false, null);

            //获取交换机
            Exchange exchange = newExchange(exchangeName, type);

            //绑定关系
            Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE,
                    exchange.getName(), routingKey, null);
            //创建队列
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareBinding(binding);
        }
    }


    /**
     * 将配置信息转换交换机信息
     */
    private Exchange newExchange(String exchangeName, String type) {
        AbstractExchange exchange = null;
        //判断类型
        switch (type) {
            //直连模式
            case MqEnum.Type.DIRECT:
                exchange = new DirectExchange(exchangeName, true, false, null);
                break;
            //广播模式：
            case MqEnum.Type.FANOUT:
                exchange = new FanoutExchange(exchangeName, true, false, null);
                break;
            //通配符模式
            case MqEnum.Type.TOPIC:
                exchange = new TopicExchange(exchangeName, true, false, null);
                break;
            case MqEnum.Type.HEADERS:
                exchange = new HeadersExchange(exchangeName, true, false, null);
                break;
            default:
                break;
        }
        //设置延迟队列
        exchange.setDelayed(false);
        return exchange;
    }
}