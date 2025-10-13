package com.example.demo.common.enums;

public enum ResponseStatus {
    SUCCESS("success"),
    FAIL("fail");

    private final String code;

    ResponseStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
