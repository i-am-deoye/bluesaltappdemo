package com.bluesaltapp.billing.core;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    public static final String QUEUE = "BILLING_QUEUE";
    private static final String TOPIC_EXCHANGE = "TOPIC_BILLING_EXCHANGE";
    private static final String ROUTING_KEY = "TOPIC_BILLING_ROUTER";

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
    MessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(queue());
        return container;
    }
}
