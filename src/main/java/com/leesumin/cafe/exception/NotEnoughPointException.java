package com.leesumin.cafe.exception;

import lombok.Getter;

@Getter
public class NotEnoughPointException extends CustomException {
    public NotEnoughPointException(ErrorEnum ee) {
        super(ee);
    }
}
