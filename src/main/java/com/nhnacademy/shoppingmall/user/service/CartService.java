package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.domain.Cart;
import com.nhnacademy.shoppingmall.user.domain.CartItem;

import java.util.List;

public interface CartService {
    Cart getOrCreateCart(String userId);
    void addItemToCart(String userId, int productId, int quantity);
    void updateCartItemQuantity(int cartItemId, int quantity);
    void removeItemFromCart(int cartItemId);
    List<CartItem> getCartItems(String userId);
    void clearCart(String userId);

}
