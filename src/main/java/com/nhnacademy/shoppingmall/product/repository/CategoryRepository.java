package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.product.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    int save(Category category);
    Optional<Category> findById(int categoryId);
    List<Category> findAll();
    int update(Category category);
    int deleteById(int categoryId);
}
