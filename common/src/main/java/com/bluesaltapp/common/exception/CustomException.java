package com.bluesaltapp.common.exception;

import com.bluesaltapp.common.Message;

public class CustomException extends Exception {

    protected Message[] messages;

    public CustomException() {
        super();

    }


    public CustomException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CustomException(Message...message) {
        this.messages = message;
    }

    public Message[] getMessages() {
        return this.messages;
    }

}
