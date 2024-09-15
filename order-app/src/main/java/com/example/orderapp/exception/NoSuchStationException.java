package com.example.orderapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoSuchStationException extends OrderException {
    private final String defaultMessage = "Станции с таким номером не существует";
    private final HttpStatus status = HttpStatus.NOT_FOUND;
}
