package com.bluesaltapp.billing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingDTO {
    private Integer id;
    private Integer customerId;
    private String amount;
    private BillingStatus status;
    private String transactionId;
}
