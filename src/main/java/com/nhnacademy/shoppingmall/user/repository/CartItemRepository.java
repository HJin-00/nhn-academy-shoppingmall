package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.user.domain.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository {
    List<CartItem> findByCartId(int cartId);

    Optional<CartItem> findByCartIdAndProductId(int cartId, int productId);

    int save(CartItem cartItem);

    int updateQuantity(int cartItemId, int quantity);

    int deleteById(int cartItemId);

    int deleteByCartId(int cartId);
}
