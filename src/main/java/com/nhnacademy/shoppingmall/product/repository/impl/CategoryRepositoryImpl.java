package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.repository.CategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public int save(Category category) {
        String sql = "INSERT INTO category (category_name) VALUES (?)";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            psmt.setString(1, category.getCategoryName());
            int result = psmt.executeUpdate();

            if (result > 0) {
                try (ResultSet rs = psmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        category.setCategoryId(rs.getInt(1));
                    }
                }
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("카테고리 저장 실패", e);
        }
    }

    @Override
    public Optional<Category> findById(int categoryId) {
        String sql = "SELECT * FROM category WHERE category_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, categoryId);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    return Optional.of(category);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("카테고리 조회 실패", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM category ORDER BY category_id";
        List<Category> list = new ArrayList<>();
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                list.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException("카테고리 목록 조회 실패", e);
        }
        return list;
    }

    @Override
    public int update(Category category) {
        String sql = "UPDATE category SET category_name = ? WHERE category_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, category.getCategoryName());
            psmt.setInt(2, category.getCategoryId());
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("카테고리 수정 실패", e);
        }
    }

    @Override
    public int deleteById(int categoryId) {
        String sql = "DELETE FROM category WHERE category_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, categoryId);
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("카테고리 삭제 실패", e);
        }
    }
}


