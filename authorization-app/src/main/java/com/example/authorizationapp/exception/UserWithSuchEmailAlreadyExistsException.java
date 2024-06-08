package com.example.authorizationapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserWithSuchEmailAlreadyExistsException extends AuthorizationException {
    private final String defaultMessage = "Пользователь с такой почтой уже существует";
    private final HttpStatus status = HttpStatus.CONFLICT;
}
