package com.nhnacademy.shoppingmall.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private int cartId;
    private String userId;
    private LocalDateTime createdAt;
}
