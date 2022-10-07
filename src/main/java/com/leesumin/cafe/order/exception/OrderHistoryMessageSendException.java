package com.leesumin.cafe.order.exception;

public class OrderHistoryMessageSendException extends RuntimeException{
    public OrderHistoryMessageSendException() {
        super("error send OrderHistoryMessage with kafka");
    }
}
