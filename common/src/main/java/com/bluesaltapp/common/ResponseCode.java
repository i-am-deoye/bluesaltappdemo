package com.bluesaltapp.common;

public enum ResponseCode {
    DEFAULT_SERVER_ERROR("99", "Server Error"),
    SUCCESSFUL("00", "Successful");

    private String code;
    private String description;

    private ResponseCode(String code, String description) {
        this.setCode(code);
        this.setDescription(description);
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
