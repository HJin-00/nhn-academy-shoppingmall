package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.user.domain.Cart;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUserId(String userId);

    int save(Cart cart);

    int deleteById(int cartId);
}
