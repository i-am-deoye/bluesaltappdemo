package com.bluesaltapp.common.exception;

import com.bluesaltapp.common.Message;

import java.util.List;

public class NotFoundException extends CustomException {

    public NotFoundException() {
    }

    public NotFoundException(Message... messages) {
        this.messages = messages;
    }


    public NotFoundException(List<Message> messages) {
        this.messages = messages != null ? messages.toArray(new Message[messages.size()]) : new Message[0];
    }

    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
