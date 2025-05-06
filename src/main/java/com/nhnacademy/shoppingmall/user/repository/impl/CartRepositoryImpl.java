package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.Cart;
import com.nhnacademy.shoppingmall.user.repository.CartRepository;

import java.sql.*;
import java.util.Optional;

public class CartRepositoryImpl implements CartRepository {
    @Override
    public Optional<Cart> findByUserId(String userId) {
        String sql = "select * from cart where user_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cart cart = new Cart(
                            rs.getInt("cart_id"),
                            rs.getString("user_id"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    return Optional.of(cart);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(Cart cart) {
        String sql = "INSERT INTO cart (user_id, created_at) VALUES (?, ?)";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cart.getUserId());
            ps.setTimestamp(2, Timestamp.valueOf(cart.getCreatedAt()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("카트 생성 실패", e);
        }
        return 0;
    }

    @Override
    public int deleteById(int cartId) {
        String sql = "DELETE FROM cart WHERE cart_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("카트 삭제 실패", e);
        }
    }
}
