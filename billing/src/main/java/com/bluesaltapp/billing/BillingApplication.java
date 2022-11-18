package com.bluesaltapp.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }
}
