package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.repository.ProductCategoryRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

    @Override
    public int save(int productId, int categoryId) {
        String sql = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?)";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ps.setInt(2, categoryId);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> findCategoryIdsByProductId(int productId) {
        String sql = "SELECT category_id FROM product_category WHERE product_id = ?";
        List<Integer> categoryIds = new ArrayList<>();
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, productId);
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    categoryIds.add(rs.getInt("category_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("카테고리 조회 실패", e);
        }
        return categoryIds;
    }

    @Override
    public int deleteByProductId(int productId) {
        String sql = "DELETE FROM product_category WHERE product_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, productId);
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("상품의 카테고리 매핑 삭제 실패", e);
        }
    }
}
