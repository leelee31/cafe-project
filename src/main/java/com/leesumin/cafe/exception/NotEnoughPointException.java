package com.leesumin.cafe.exception;

import lombok.Getter;

@Getter
public class NotEnoughPointException extends RuntimeException {
    private final ErrorEnum error;

    public NotEnoughPointException(ErrorEnum ee) {
        super(ee.getMessage());
        this.error = ee;
    }
}
