package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.repository.AddressRepository;
import com.nhnacademy.shoppingmall.user.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    @Override
    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void updateAddress(Address address) {
        addressRepository.update(address);
    }

    @Override
    public void deleteAddressByUserIdAndAddressName(String userId, String addressName) {
        addressRepository.deleteByUserIdAndAddressName(userId, addressName);
    }

    @Override
    public Address findAddressByUserId(String userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAllAddresses();
    }
}
