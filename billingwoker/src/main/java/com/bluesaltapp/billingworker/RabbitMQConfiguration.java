package com.bluesaltapp.billingworker;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {
    public static final String QUEUE = "WORKERI_QUEUE";
    private static final String TOPIC_EXCHANGE = "TOPIC_WORKERI_EXCHANGE";
    private static final String ROUTING_KEY = "TOPIC_WORKERI_ROUTER";

    @Bean
    public Queue queue() {
        return QueueBuilder
                .durable(QUEUE)
                .autoDelete()
                .exclusive()
                .build();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder
                .topicExchange(TOPIC_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    Binding binding() {
        return new Binding(QUEUE, Binding.DestinationType.QUEUE, TOPIC_EXCHANGE, ROUTING_KEY, null);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }

    @Bean
    MessageListenerContainer listenerContainer(RabbitTemplate rabbitTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(queue());
        container.setMessageListener(new QueueMessageListener(rabbitTemplate));
        return container;
    }
}
