package com.leesumin.cafe.exception;

import lombok.Getter;

@Getter
public class PointException extends BusinessException {
    public PointException(ErrorEnum error) {
        super(error);
    }
}