package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.domain.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    List<CartItem> getCartItems(int cartId);
    Optional<CartItem> getCartItem(int cartId, int productId);
    void addCartItem(CartItem cartItem);
    void updateQuantity(int cartItemId, int quantity);
    void removeCartItem(int cartItemId);
    void removeCartItemsByCartId(int cartId);
}
