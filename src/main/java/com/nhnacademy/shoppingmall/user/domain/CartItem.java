package com.nhnacademy.shoppingmall.user.domain;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private int cartItemId;   // 장바구니 상품 ID (PK)
    private int cartId;       // 장바구니 ID (FK)
    private int productId;    // 상품 ID (FK)
    private int quantity;     // 수량
    private String productName;
    private int price;

    public CartItem(int cartItemId, int cartId, int productId, int quantity) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }
}