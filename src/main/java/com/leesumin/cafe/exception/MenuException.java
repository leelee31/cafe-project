package com.leesumin.cafe.exception;

public class MenuException extends BusinessException{
    public MenuException(ErrorEnum error) {
        super(error);
    }
}
