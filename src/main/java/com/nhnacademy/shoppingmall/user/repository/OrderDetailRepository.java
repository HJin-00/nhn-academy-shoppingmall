package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.user.domain.OrderDetail;

import java.util.List;

public interface OrderDetailRepository {
    int save(OrderDetail orderDetail);
    List<OrderDetail> findByOrderId(int orderId);
}
