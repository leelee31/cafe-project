package com.leesumin.cafe.exception;

public class CustomException extends RuntimeException {
    private ErrorEnum error;

    public CustomException(ErrorEnum error) {
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