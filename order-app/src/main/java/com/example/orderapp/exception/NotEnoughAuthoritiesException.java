package com.example.orderapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotEnoughAuthoritiesException extends OrderException {
    private final String defaultMessage = "Недостаточно прав для просмотра этого заказа";
    private final HttpStatus status = HttpStatus.FORBIDDEN;
}
