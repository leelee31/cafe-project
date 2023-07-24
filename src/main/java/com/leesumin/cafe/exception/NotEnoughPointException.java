package com.leesumin.cafe.exception;

import lombok.Getter;

@Getter
public class NotEnoughPointException extends BusinessException {
    public NotEnoughPointException(ErrorEnum ee) {
        super(ee);
    }
}