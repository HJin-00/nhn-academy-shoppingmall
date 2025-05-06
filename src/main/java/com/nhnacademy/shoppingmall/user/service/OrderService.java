package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.CartItem;
import com.nhnacademy.shoppingmall.user.domain.Order;

import java.util.List;

public interface OrderService {
    void placeOrder(String userId, List<CartItem> cartItems, int usedPoint);
    Page<Order> getPagedOrderList(int page, int pageSize);
}
