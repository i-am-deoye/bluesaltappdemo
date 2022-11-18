package com.bluesaltapp.billing.core;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BillingConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
