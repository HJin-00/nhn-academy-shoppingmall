package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public int save(Product product) {
        String sql = "INSERT INTO products (product_name, price, thumbnail_image, detail_image, product_description, created_at, updated_at, stock) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, product.getProductName());
            psmt.setInt(2, product.getPrice());
            psmt.setString(3, product.getThumbnailImage());
            psmt.setString(4, product.getDetailImage());
            psmt.setString(5, product.getProductDescription());
            psmt.setTimestamp(6, Timestamp.valueOf(product.getCreatedAt()));
            if(product.getUpdatedAt() != null) {
                psmt.setTimestamp(7, Timestamp.valueOf(product.getUpdatedAt()));
            }else psmt.setNull(7, Types.TIMESTAMP);
            psmt.setInt(8,product.getStock());

            int result = psmt.executeUpdate();
            try(ResultSet rs = psmt.getGeneratedKeys()) {
                if(rs.next()) {
                    int generatedId = rs.getInt(1);
                    product.setProductId(generatedId);
                }
            }
            return result;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(int productId) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setInt(1, productId);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products ORDER BY created_at DESC";
        List<Product> products = new ArrayList<>();
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapRow(rs));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public int update(Product product) {
        String sql = "UPDATE products SET product_name = ?, price = ?, thumbnail_image = ?, detail_image = ?, product_description = ?, updated_at = ?, stock = ? " +
                "WHERE product_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, product.getProductName());
            psmt.setInt(2, product.getPrice());
            psmt.setString(3, product.getThumbnailImage());
            psmt.setString(4, product.getDetailImage());
            psmt.setString(5, product.getProductDescription());
            psmt.setTimestamp(6, Timestamp.valueOf(product.getUpdatedAt()));
            psmt.setInt(7, product.getStock());
            psmt.setInt(8, product.getProductId());

            return psmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("상품 수정 실패", e);
        }
    }

    @Override
    public int deleteById(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, productId);
            return psmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("상품 삭제 실패", e);
        }
    }

    @Override
    public Page<Product> findPage(int offset, int limit) {
        String sql = "SELECT * FROM products ORDER BY created_at DESC LIMIT ? OFFSET ?";
        List<Product> products = new ArrayList<>();

        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, limit);
            psmt.setInt(2, offset);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("상품 페이징 조회 실패", e);
        }

        long totalCount = count();
        return new Page<>(products, totalCount);
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM products";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("상품 수 조회 실패", e);
        }

        return 0;
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setPrice(rs.getInt("price"));
        product.setThumbnailImage(rs.getString("thumbnail_image"));
        product.setDetailImage(rs.getString("detail_image"));
        product.setProductDescription(rs.getString("product_description"));
        product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        Timestamp updated = rs.getTimestamp("updated_at");
        product.setUpdatedAt(updated != null ? updated.toLocalDateTime() : null);
        product.setStock(rs.getInt("stock"));
        return product;
    }
}
