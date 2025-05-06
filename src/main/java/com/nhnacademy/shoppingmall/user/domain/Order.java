package com.nhnacademy.shoppingmall.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderId;            // 주문 ID (PK)
    private String userId;          // 회원 ID (FK)
    private int usedPoint;          // 사용 포인트
    private int earnPoint;          // 적립 포인트
    private String orderStatus;     // 주문 상태 (예: 주문완료, 배송대기 등)
    private LocalDateTime createdAt; // 주문일자
}
