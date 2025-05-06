package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.CartItem;
import com.nhnacademy.shoppingmall.user.repository.CartItemRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartItemRepositoryImpl implements CartItemRepository {
    @Override
    public List<CartItem> findByCartId(int cartId) {
        String sql = "SELECT ci.cart_item_id, ci.cart_id, ci.product_id, ci.quantity, p.product_name, p.price " +
                "FROM cart_items ci " +
                "JOIN products p ON ci.product_id = p.product_id " +
                "WHERE ci.cart_id = ?";
        List<CartItem> items = new ArrayList<>();
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem(
                            rs.getInt("cart_item_id"),
                            rs.getInt("cart_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getString("product_name"),
                            rs.getInt("price")
                    );
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("카트 아이템 조회 실패", e);
        }
        return items;
    }

    @Override
    public Optional<CartItem> findByCartIdAndProductId(int cartId, int productId) {
        String sql = "SELECT ci.cart_item_id, ci.cart_id, ci.product_id, ci.quantity, p.product_name, p.price " +
                "FROM cart_items ci " +
                "JOIN products p ON ci.product_id = p.product_id " +
                "WHERE ci.cart_id = ? AND ci.product_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new CartItem(
                            rs.getInt("cart_item_id"),
                            rs.getInt("cart_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getString("product_name"),
                            rs.getInt("price")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("카트 아이템 조회 실패", e);
        }
        return Optional.empty();
    }

    @Override
    public int save(CartItem cartItem) {
        String sql = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItem.getCartId());
            ps.setInt(2, cartItem.getProductId());
            ps.setInt(3, cartItem.getQuantity());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("카트 아이템 저장 실패", e);
        }
    }

    @Override
    public int updateQuantity(int cartItemId, int quantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("카트 아이템 수량 수정 실패", e);
        }
    }

    @Override
    public int deleteById(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            return ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByCartId(int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("카트 아이템 삭제 실패", e);
        }
    }
}
