package com.example.orderapp.handler;

import com.example.orderapp.exception.OrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {
    private static final String LOGIN_INCORRECT_DATA = "Неверный логин и/или пароль";
    private static final String EMAIL_NOT_EXIST = "Пользователя с почтой %s не существует";
    private static final String UNKNOWN_ERROR = "Неизвестная ошибка";

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<String> handleOrderException(OrderException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getDefaultMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleAUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(EMAIL_NOT_EXIST.formatted(ex));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ignored) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(LOGIN_INCORRECT_DATA);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getFieldError() == null || ex.getFieldError().getDefaultMessage() == null
                        ? ""
                        : ex.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleOtherException(Throwable ignored) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(UNKNOWN_ERROR);
    }
}
