package com.bluesaltapp.common.rabbit_messages;


import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RQMessage<Value extends IRabbitMQ> implements Serializable {

    private String messageId;
    private Value message;
    private Date messageDate;


    public static RQMessage<IRabbitMQ> message(IRabbitMQ value) {
        return RQMessage.builder()
                .messageId(UUID.randomUUID().toString())
                .message(value)
                .messageDate(new Date())
                .build();
    }
}
