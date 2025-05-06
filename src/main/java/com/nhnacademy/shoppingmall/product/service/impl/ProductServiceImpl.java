package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;

    }

    @Override
    public int save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public int update(Product product) {
        return productRepository.update(product);
    }

    @Override
    public int deleteById(int productId) {
        return productRepository.deleteById(productId);
    }

    @Override
    public Page<Product> getProductPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return productRepository.findPage(offset, pageSize);
    }

    @Override
    public int addProduct(Product product, int categoryId) {
        productRepository.save(product);
        int productId = product.getProductId();
        return productCategoryRepository.save(productId, categoryId);
    }
}
