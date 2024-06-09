package com.example.orderapp.service;

import com.example.orderapp.dto.CreateOrderRequest;
import com.example.orderapp.entity.Order;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    @Transactional
    Order createOrder(CreateOrderRequest request);

    @Transactional
    Order getOrder(int orderId);

    @Transactional
    void processOrders();
}
