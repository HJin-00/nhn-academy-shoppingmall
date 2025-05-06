package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.domain.Cart;
import com.nhnacademy.shoppingmall.user.domain.CartItem;
import com.nhnacademy.shoppingmall.user.repository.CartItemRepository;
import com.nhnacademy.shoppingmall.user.repository.CartRepository;
import com.nhnacademy.shoppingmall.user.service.CartService;

import java.time.LocalDateTime;
import java.util.List;

public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getOrCreateCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart(0, userId, LocalDateTime.now());
                    int cartId = cartRepository.save(cart);
                    return new Cart(cartId, userId, cart.getCreatedAt());
                });
    }

    @Override
    public void addItemToCart(String userId, int productId, int quantity) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.findByCartIdAndProductId(cart.getCartId(), productId)
                .ifPresentOrElse(
                        existing -> cartItemRepository.updateQuantity(existing.getCartItemId(), existing.getQuantity() + quantity),
                        () -> cartItemRepository.save(new CartItem(0, cart.getCartId(), productId, quantity))
                );
    }

    @Override
    public void updateCartItemQuantity(int cartItemId, int quantity) {
        cartItemRepository.updateQuantity(cartItemId, quantity);
    }

    @Override
    public void removeItemFromCart(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public List<CartItem> getCartItems(String userId) {
        return cartRepository.findByUserId(userId)
                .map(cart -> cartItemRepository.findByCartId(cart.getCartId()))
                .orElse(List.of());
    }

    @Override
    public void clearCart(String userId) {
        cartRepository.findByUserId(userId)
                .ifPresent(cart -> cartItemRepository.deleteByCartId(cart.getCartId()));
    }
}