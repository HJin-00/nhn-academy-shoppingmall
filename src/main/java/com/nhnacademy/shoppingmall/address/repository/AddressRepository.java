package com.nhnacademy.shoppingmall.address.repository;

import com.nhnacademy.shoppingmall.address.domain.Address;

import java.util.List;

public interface AddressRepository {
    int save(Address address);
    int update(Address address);
    int deleteByUserIdAndAddressName(String userId, String addressName);
    Address findByUserId(String userId);
    List<Address> findAllAddresses();
}
