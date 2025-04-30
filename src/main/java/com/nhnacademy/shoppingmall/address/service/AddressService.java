package com.nhnacademy.shoppingmall.address.service;

import com.nhnacademy.shoppingmall.address.domain.Address;

import java.util.List;

public interface AddressService {
    void saveAddress(Address address);
    void updateAddress(Address address);
    void deleteAddressByUserIdAndAddressName(String userId, String addressName);
    Address findAddressByUserId(String userId);
    List<Address> getAllAddresses();

}
