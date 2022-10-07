package com.leesumin.cafe.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException() {
        super("해당 고객의 정보가 없습니다.");
    }
}
