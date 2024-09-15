package com.example.orderapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidAuthorizationException extends OrderException {
    private final String defaultMessage = "Авторизация не пройдена";
    private final HttpStatus status = HttpStatus.UNAUTHORIZED;
}

