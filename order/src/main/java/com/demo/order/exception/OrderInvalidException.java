package com.demo.order.exception;

public class OrderInvalidException extends Exception {

    private String message;

    public OrderInvalidException(String message) {
        super();
        this.message = message;
    }
}
