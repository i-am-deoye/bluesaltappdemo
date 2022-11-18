package com.bluesaltapp.billing.service;


import com.bluesaltapp.billing.core.RabbitMQConfiguration;
import com.bluesaltapp.billing.core.response.FraudResponse;
import com.bluesaltapp.billing.model.Billing;
import com.bluesaltapp.billing.model.BillingDTO;
import com.bluesaltapp.billing.model.BillingStatus;
import com.bluesaltapp.billing.model.BillingVM;
import com.bluesaltapp.billing.repository.BillingRepository;
import com.bluesaltapp.common.GlobalMessage;
import com.bluesaltapp.common.IResponsePayload;
import com.bluesaltapp.common.Message;
import com.bluesaltapp.common.ResponsePayload;
import com.bluesaltapp.common.exception.CustomException;
import com.bluesaltapp.common.rabbit_messages.BillingMessage;
import com.bluesaltapp.common.rabbit_messages.FundedMessage;
import com.bluesaltapp.common.rabbit_messages.RQMessage;
import com.bluesaltapp.common.service.BaseValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class BillingService extends BaseValidationService {

    private final BillingRepository billingRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    public BillingDTO fundCustomerAccount(BillingVM request) throws CustomException {
        validateSaveEntity(request, "amount", "customerId", "id");

        /*ResponsePayload<FraudResponse> fraudResponse = (ResponsePayload<FraudResponse>) restTemplate.getForObject("http://localhost:8085/api/v1/fraud-check/{customerId}",
                Object.class, request.customerId());

        fraudResponse.validateDate();
        FraudResponse response = fraudResponse.getData().get(0);

        if (response.isFraud()) {
            GlobalMessage message = new GlobalMessage("Customer is fraudulent", "", Message.Severity.ERROR );
            throw new CustomException(message);
        }*/

        Billing billing = Billing.builder()
                .status(BillingStatus.Pending)
                .amount(request.amount())
                .customerId(request.customerId())
                .transactionId(UUID.randomUUID().toString())
                .build();

        billingRepository.saveAndFlush(billing);

        BillingMessage message = BillingMessage.builder()
                .amount(billing.getAmount())
                .customerId(billing.getCustomerId())
                .transactionId(billing.getTransactionId())
                .build();

        RQMessage rqMessage = RQMessage.message(message);
        rabbitTemplate.convertAndSend("TOPIC_WORKER_EXCHANGE", "TOPIC_WORKER_ROUTER", rqMessage);


        return BillingDTO.builder()
                .amount(billing.getAmount())
                .customerId(billing.getCustomerId())
                .id(billing.getId())
                .transactionId(billing.getTransactionId())
                .status(billing.getStatus())
                .build();
    }

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE)
    public void receiveMessage(final RQMessage<FundedMessage> rqMessage) {
        log.info("receiveMessage {}", rqMessage);
        FundedMessage message = rqMessage.getMessage();
        Billing billing = billingRepository.findBillingByTransactionId(message.getTransactionId()).get();
        if (message.getIsSuccessfull()) {
            log.info("receiveMessage SUCCESSS {}", rqMessage);
            billing.setStatus(BillingStatus.Successful);
            billingRepository.save(billing);
        }
    }

}
