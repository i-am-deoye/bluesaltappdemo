package com.bluesaltapp.billingworker;

import com.bluesaltapp.common.rabbit_messages.BillingMessage;
import com.bluesaltapp.common.rabbit_messages.FundedMessage;
import com.bluesaltapp.common.rabbit_messages.RQMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
@Slf4j
public class QueueMessageListener  {

    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE)
    public void receiveMessage(final RQMessage<BillingMessage> rqMessage) {
        BillingMessage message = rqMessage.getMessage();
        log.info("receiveMessage worker {}", rqMessage);
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                FundedMessage fundedMessage = FundedMessage
                        .builder()
                        .amount("1000")
                        .isSuccessfull(true)
                        .transactionId(message.getTransactionId())
                        .build();

                RQMessage rqMessage = RQMessage.message(fundedMessage);
                rabbitTemplate.convertAndSend("TOPIC_BILLING_EXCHANGE", "TOPIC_BILLING_ROUTER", rqMessage);
            }
        }, 100, 0, TimeUnit.MILLISECONDS);
    }
}
