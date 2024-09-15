package com.example.authorizationapp.exception;

import org.springframework.http.HttpStatus;

public abstract class AuthorizationException extends RuntimeException {
    public abstract String getDefaultMessage();
    public abstract HttpStatus getStatus();
}
