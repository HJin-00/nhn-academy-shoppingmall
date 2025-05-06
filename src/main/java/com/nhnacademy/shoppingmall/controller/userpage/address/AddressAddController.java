package com.nhnacademy.shoppingmall.controller.userpage.address;

import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.AddressService;
import com.nhnacademy.shoppingmall.user.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

@RequestMapping(method = RequestMapping.Method.POST, value = "/addressAdd.do")
public class AddressAddController implements BaseController {

    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        String addressName = req.getParameter("address_name");
        String addressDetail = req.getParameter("address_detail");

        Address address = new Address(userId,addressName, LocalDateTime.now(),addressDetail);
        address.setUserId(userId);
        address.setAddressName(addressName);
        address.setAddressDetail(addressDetail);
        address.setCreatedAt(LocalDateTime.now());

        try{
            addressService.saveAddress(address);
        }catch (Exception e){
            return "redirect:/error";
        }
        return "redirect:/address.do";
    }
}
