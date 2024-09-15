package com.example.orderapp.exception;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidRequestException extends OrderException {
    private final String defaultMessage = "Некорректный запрос";
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
}
