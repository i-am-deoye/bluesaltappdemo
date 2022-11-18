package com.bluesaltapp.billing.model;

import com.bluesaltapp.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Billing extends BaseEntity {
    @Id
    @SequenceGenerator(
            name = "billing_id_sequence",
            sequenceName = "billing_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "billing_id_sequence"
    )
    private Integer id;
    private Integer customerId;
    private String amount;
    private BillingStatus status;
    private String transactionId;
}