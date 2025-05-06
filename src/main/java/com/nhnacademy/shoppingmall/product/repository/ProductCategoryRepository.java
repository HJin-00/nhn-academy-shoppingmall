package com.nhnacademy.shoppingmall.product.repository;

import java.util.List;

public interface ProductCategoryRepository {
    int save(int productId, int categoryId);
    List<Integer> findCategoryIdsByProductId(int productId);
    int deleteByProductId(int productId);
}
