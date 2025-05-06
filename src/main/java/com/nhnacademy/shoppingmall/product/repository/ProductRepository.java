package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    int save(Product product);
    Optional<Product> findById(int productId);

    List<Product> findAll();

    int update(Product product);

    int deleteById(int productId);

    Page<Product> findPage(int offset, int limit);

    long count();

    int updateStock(int productId, int newstock);





}
