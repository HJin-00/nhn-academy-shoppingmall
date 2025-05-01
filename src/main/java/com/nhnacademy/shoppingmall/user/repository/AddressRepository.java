package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.user.domain.Address;

import java.util.List;

public interface AddressRepository {
    int save(Address address);
    int update(Address address);
    int deleteByUserIdAndAddressName(String userId, String addressName);
    Address findByUserId(String userId);
    List<Address> findAllAddresses();
}
