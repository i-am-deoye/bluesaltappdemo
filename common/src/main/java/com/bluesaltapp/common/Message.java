
package com.bluesaltapp.common;

public interface Message {

    String getSummary();

    void setSummary(String summary);

    String getDetail();

    void setDetail(String detail);

    Severity getSeverity();

    enum Severity {
        SUCCESS,
        INFO,
        WARNING,
        ERROR,
        FATAL,
        DANGER,
        ACCESS_DENIED
    }
}