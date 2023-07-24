package com.leesumin.cafe.exception;

public class BusinessException extends RuntimeException {
    private ErrorEnum error;

    public BusinessException(ErrorEnum error) {
        super(error.getMessage());
        this.error = error;
    }

    public int getCode() {
        return error.getCode();
    }

    public String getMessage() {
        return error.getMessage();
    }
}