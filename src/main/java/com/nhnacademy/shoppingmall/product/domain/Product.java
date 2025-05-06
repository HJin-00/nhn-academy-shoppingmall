package com.nhnacademy.shoppingmall.product.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Product {
    private int productId;
    private String productName;
    private int price;
    private String thumbnailImage;
    private String detailImage;
    private String productDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int stock;

    public Product() {}

    public Product(String productName, int price, String thumbnailImage, String detailImage, String productDescription, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productName = productName;
        this.price = price;
        this.thumbnailImage = thumbnailImage;
        this.detailImage = detailImage;
        this.productDescription = productDescription;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
