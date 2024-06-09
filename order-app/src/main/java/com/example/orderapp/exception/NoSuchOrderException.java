package com.example.orderapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoSuchOrderException extends OrderException {
    private final String defaultMessage = "Заказа с таким номером не существует";
    private final HttpStatus status = HttpStatus.NOT_FOUND;
}