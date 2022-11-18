package com.bluesaltapp.common;

import java.util.List;

public interface IResponsePayload<M> {

    boolean isValid();

    void setValid(boolean valid);

    List<M> getData();

    void addData(M dataEntry);

    List<Message> getMessages();

    void addMessage(Message msg);

    void addMessage(String msg, String detail, Message.Severity severity);

    void addMessage(String msg, Message.Severity severity);
}
