package com.example.orderapp.repository;

import com.example.orderapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {
    List<Order> findAllByStatus(int status);
}
