package com.bluesaltapp.common.rabbit_messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingMessage implements IRabbitMQ {
    private String amount;
    private String transactionId;
    private Integer customerId;
}
