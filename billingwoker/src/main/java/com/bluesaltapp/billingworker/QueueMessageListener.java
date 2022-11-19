package com.bluesaltapp.billingworker;

import com.bluesaltapp.common.rabbit_messages.BillingMessage;
import com.bluesaltapp.common.rabbit_messages.FundedMessage;
import com.bluesaltapp.common.rabbit_messages.RQMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
public class QueueMessageListener implements MessageListener {

    private RabbitTemplate rabbitTemplate;

    @Override
    public void onMessage(Message message1) {
        RQMessage<BillingMessage> rqMessage = (RQMessage<BillingMessage>) SerializationUtils.deserialize(message1.getBody());
        final BillingMessage message = rqMessage.getMessage();
        log.info("receiveMessage worker {}", rqMessage);
        try {
            Thread.sleep(100);
            FundedMessage fundedMessage = FundedMessage
                    .builder()
                    .amount("1000")
                    .isSuccessfull(true)
                    .transactionId(message.getTransactionId())
                    .build();

            RQMessage sendMessageBody = RQMessage.message(fundedMessage);
            byte[] body = SerializationUtils.serialize(sendMessageBody);
            Message sendMessage = new Message(body);

            rabbitTemplate.convertAndSend("TOPIC_BILLING_EXCHANGE", "TOPIC_BILLING_ROUTER", sendMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
