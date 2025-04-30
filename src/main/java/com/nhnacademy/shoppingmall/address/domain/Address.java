package com.nhnacademy.shoppingmall.address.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Address {
    private String userId;
    private String addressName;
    private LocalDateTime createdAt;
    private String addressDetail;
    public Address(){}

    public Address(String userId, String addressName, LocalDateTime createdAt, String addressDetail) {
        this.userId = userId;
        this.addressName = addressName;
        this.createdAt = createdAt;
        this.addressDetail = addressDetail;
    }

    @Override
    public String toString() {
        return "Address{" +
                "userId=" + userId +
                ", addressName ='" + addressName + '\'' +
                ", addressDetail='"+ addressDetail + '\''+
                '}';
    }
}
