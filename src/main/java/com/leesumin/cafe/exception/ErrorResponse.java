package com.leesumin.cafe.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int errorCode;
    private final String errorMessage;

    private ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static ErrorResponse of(NotEnoughPointException e) {
        return new ErrorResponse(e.getError().getCode(), e.getError().getMessage());
    }
}
