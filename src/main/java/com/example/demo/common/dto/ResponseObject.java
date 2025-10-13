package com.example.demo.common.dto;

import com.example.demo.common.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseObject<T>(String status, String message, T data) {

    public static <T> ResponseObject<T> success() {
        return new ResponseObject<>(ResponseStatus.SUCCESS.getCode(), null, null);
    }

    public static <T> ResponseObject<T> success(T data) {
        return new ResponseObject<>(ResponseStatus.SUCCESS.getCode(), null, data);
    }

    public static <T> ResponseObject<T> fail() {
        return new ResponseObject<>(ResponseStatus.FAIL.getCode(), null, null);
    }

    public static <T> ResponseObject<T> fail(String message) {
        return new ResponseObject<>(ResponseStatus.FAIL.getCode(), message, null);
    }
}
