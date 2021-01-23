package org.xr.happy.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    static final String topicExchangeName = "blog-exchange";

    static final String queueName = "xr-blog-love";

    /**申明队列
     * @return
     */
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }



    /**申明交换机
     * @return
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    /**将交换机与队列绑定
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("xr-blog-love");
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



