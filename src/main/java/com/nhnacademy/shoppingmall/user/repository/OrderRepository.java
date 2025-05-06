package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.user.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    int save(Order order);
    Optional<Order> findById(int orderId);
    List<Order> findPage(int offset, int limit);
    long count();
}
