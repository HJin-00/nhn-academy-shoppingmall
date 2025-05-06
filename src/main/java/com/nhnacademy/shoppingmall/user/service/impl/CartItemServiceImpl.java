package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.domain.CartItem;
import com.nhnacademy.shoppingmall.user.repository.CartItemRepository;
import com.nhnacademy.shoppingmall.user.service.CartItemService;

import java.util.List;
import java.util.Optional;

public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItem> getCartItems(int cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    @Override
    public Optional<CartItem> getCartItem(int cartId, int productId) {
        return cartItemRepository.findByCartIdAndProductId(cartId, productId);
    }

    @Override
    public void addCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public void updateQuantity(int cartItemId, int quantity) {
        cartItemRepository.updateQuantity(cartItemId, quantity);
    }

    @Override
    public void removeCartItem(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void removeCartItemsByCartId(int cartId) {
        cartItemRepository.deleteByCartId(cartId);
    }
}
