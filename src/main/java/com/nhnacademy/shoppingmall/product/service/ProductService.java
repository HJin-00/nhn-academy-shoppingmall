package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    int save(Product product);  // 상품 등록

    Optional<Product> findById(int productId);  // 상품 상세 조회

    List<Product> findAll();  // 전체 상품 조회

    int update(Product product);  // 상품 수정

    int deleteById(int productId);  // 상품 삭제

    Page<Product> getProductPage(int page, int pageSize);  //

    int addProduct(Product product, int categoryId);
}
