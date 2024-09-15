package com.example.orderapp.exception;

import org.springframework.http.HttpStatus;

public abstract class OrderException extends RuntimeException {
    public abstract String getDefaultMessage();
    public abstract HttpStatus getStatus();
}
