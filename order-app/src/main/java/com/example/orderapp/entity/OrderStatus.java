package com.example.orderapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    CHECK(1, "Check"),
    SUCCESS(2, "Success"),
    REJECTION(3, "Rejection");

    private final int value;
    private final String name;
}
