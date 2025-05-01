package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.domain.Address;

import java.util.List;

public interface AddressService {
    void saveAddress(Address address);
    void updateAddress(Address address);
    void deleteAddressByUserIdAndAddressName(String userId, String addressName);
    Address findAddressByUserId(String userId);
    List<Address> getAllAddresses();

}
