package com.bluesaltapp.common;

import com.bluesaltapp.common.IResponsePayload;
import com.bluesaltapp.common.Message;
import com.bluesaltapp.common.exception.DataValidationException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ResponsePayload<M> implements IResponsePayload<M> {

    private boolean valid;

    private List<M> data;

    private List<Message> messages;

    public ResponsePayload() {
        valid = false;
    }

    public ResponsePayload(boolean valid, List<M> data, List<Message> messages) {
        this.valid = valid;
        this.data = data;
        this.messages = messages;
    }

    public ResponsePayload(boolean valid) {
        this.valid = valid;
    }

    @JsonProperty("responseCode")
    public String getResponseCode() {
        return valid ? ResponseCode.SUCCESSFUL.getCode() : ResponseCode.DEFAULT_SERVER_ERROR.getCode();
    }

    @Override
    public List<M> getData() {
        return data;
    }

    @Override
    public void addMessage(String msg, Message.Severity severity) {
        addMessage(msg, null, severity);
    }


    @Override
    public void addMessage(String msg, String detail, Message.Severity severity) {
        if (msg != null || detail != null) {
            addMessage(new GlobalMessage(msg, detail, severity));
        }
    }


    @Override
    public void addData(M dataEntry) {
        if (dataEntry != null) {
            if (this.data == null) {
                this.data = new ArrayList<>();
            }
            this.data.add(dataEntry);
        }
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }


    @Override
    public void addMessage(Message msg) {
        if (msg != null) {
            if (this.messages == null) {
                this.messages = new ArrayList();
            }
            this.messages.add(msg);
        }
    }


    public boolean hasMessages() {
        return this.messages != null && !this.messages.isEmpty();
    }

    public void validateDate() throws DataValidationException {
        if (data.isEmpty()) {
            GlobalMessage message = new GlobalMessage("Payload should contain data", "", Message.Severity.ERROR );
            throw new DataValidationException(message);
        }
    }
}