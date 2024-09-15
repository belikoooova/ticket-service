package com.example.orderapp.service;

import com.example.orderapp.auth.User;
import com.example.orderapp.dto.CreateOrderRequest;
import com.example.orderapp.entity.Order;
import com.example.orderapp.entity.OrderStatus;
import com.example.orderapp.exception.NoSuchOrderException;
import com.example.orderapp.exception.NoSuchStationException;
import com.example.orderapp.exception.NotEnoughAuthoritiesException;
import com.example.orderapp.repository.OrderDao;
import com.example.orderapp.repository.StationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final int MIN_DELAY = 1000;
    private static final int MAX_DELAY = 5000;
    private static final int SCHEDULER_TIMEOUT = 10000;

    private final Random random = new Random();
    private final StationDao stationDao;
    private final OrderDao orderDao;

    @Override
    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        if (!stationDao.existsById(request.getToStationId()) || !stationDao.existsById(request.getFromStationId())) {
            throw new NoSuchStationException();
        }

        User user = getCurrentUser();

        return orderDao.save(Order.builder()
                        .status(OrderStatus.CHECK.getValue())
                        .created(OffsetDateTime.now())
                        .userId(user.getId())
                        .fromStationId(request.getFromStationId())
                        .toStationId(request.getToStationId())
                        .build());
    }

    @Override
    @Transactional
    public Order getOrder(int orderId) {
        Order order = orderDao.findById(orderId).orElseThrow(NoSuchOrderException::new);

        User user = getCurrentUser();

        if (user.getId() != order.getUserId()) {
            throw new NotEnoughAuthoritiesException();
        }

        return order;
    }

    @Transactional
    @Override
    @Scheduled(fixedRate = SCHEDULER_TIMEOUT)
    public void processOrders() {
        List<Order> ordersToProcess = orderDao.findAllByStatus(OrderStatus.CHECK.getValue());
        ordersToProcess.forEach(this::processOrder);
    }

    @Transactional
    public void processOrder(Order order) {
        try {
            Thread.sleep(random.nextInt(MIN_DELAY, MAX_DELAY));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int newStatus = random.nextBoolean() ? OrderStatus.SUCCESS.getValue() : OrderStatus.REJECTION.getValue();
        order.setStatus(newStatus);
        orderDao.save(order);
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
