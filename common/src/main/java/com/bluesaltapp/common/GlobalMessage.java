package com.bluesaltapp.common;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
public class GlobalMessage implements Message {

    private String summary;

    private String detail;

    private Message.Severity severity;

    public GlobalMessage() {
    }

    public GlobalMessage(String summary, String detail, Severity severity) {
        this.summary = summary;
        this.detail = detail;
        this.severity = severity;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
}