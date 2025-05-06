package com.nhnacademy.shoppingmall.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private int orderDetailId;  // 주문상세 ID (PK)
    private int orderId;        // 주문 ID (FK)
    private int productId;      // 상품 ID (FK)
    private int quantity;       // 수량
    private int price;
}
