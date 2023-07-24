package com.leesumin.cafe.exception;

import lombok.Getter;

@Getter
public class CustomerException extends BusinessException {
    public CustomerException(ErrorEnum error) {
        super(error);
    }
}