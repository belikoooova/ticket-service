package com.example.orderapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalAuthorizationException extends OrderException {
    private final String defaultMessage = "Ошибка при авторизации";
    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
}
