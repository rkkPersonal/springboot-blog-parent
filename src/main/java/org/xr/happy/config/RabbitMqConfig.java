package org.xr.happy.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String topicExchangeName = "blog-exchange";

    public static final String queueName = "xr-blog-love";

    /**
     * 延时队列
     * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
     * 队列里所有的message都有统一的失效时间
     */
    public static final String DELAY_QUEUE = "delay.queue";

    /**
     * 延时的交换器
     */
    public static final String DELAY_EXCHANGE = "delay.queue.exchange";


    /**
     * 实际消费队列
     * message失效后进入的队列，也就是实际的消费队列
     */
    public static final String PROCESS_QUEUE = "process.queue";

    /**
     * 处理的交换器
     */
    public static final String PROCESS_EXCHANGE = "process.queue.exchange";
    /**
     * 超时时间
     */
    public static Long QUEUE_EXPIRATION = 4000L;


    /**
     * 申明队列
     *
     * @return
     */
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }


    /**
     * 申明交换机
     *
     * @return
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    /**
     * 将交换机与队列绑定
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("xr-blog-love");
    }

    /*-----------------------------------------------------------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------------------------------------------------------*/
    /**
     * 配置延时交换器
     *
     * @return
     */
    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    /**
     * 配置延时队列
     *
     * @return
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE)
                // DLX，dead letter发送到的exchange ,设置死信队列交换器到处理交换器
                .withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE)
                // dead letter携带的routing key，配置处理队列的路由key
                .withArgument("x-dead-letter-routing-key", PROCESS_QUEUE)
                // 设置过期时间
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .build();
    }

    /**
     * 将delayQueue2绑定延时交换机中，routingKey为队列名称
     *
     * @return
     */
    @Bean
    Binding queueTTLBinding() {
        return BindingBuilder
                .bind(delayQueue())
                .to(delayExchange())
                .with(DELAY_QUEUE);
    }


    /**
     * 设置处理队列
     *
     * @return
     */
    @Bean
    public Queue delayProcess() {
        return QueueBuilder
                .durable(PROCESS_QUEUE)
                .build();
    }

    /**
     * 配置处理交换器
     *
     * @return
     */
    @Bean
    DirectExchange processExchange() {
        return new DirectExchange(PROCESS_EXCHANGE);
    }

    /**
     * 将DLX绑定到实际消费队列
     *
     * @return
     */
    @Bean
    Binding processBinding() {
        return BindingBuilder
                .bind(delayProcess())
                .to(processExchange())
                .with(PROCESS_QUEUE);
    }


    /** 申明处理消息的适配器,指明用哪个方法处理接收消息,
     * 这里指明了用Receiver的receiveMessage()方法接收消息
     * @param receiver 这是自己注入的对象,里面有一个receiveMessage方法
     * @return
     */
/*    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }*/

    /** 申明一个Listener容器,其实就是对应一个消费者(不知道我这样理解对不对)
     * 需要设置连接消息,指明监听哪个queue,受到消息的处理方法
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
/*    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }*/

}



