package com.nhnacademy.shoppingmall.controller.userpage.address;

import com.nhnacademy.shoppingmall.user.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.AddressService;
import com.nhnacademy.shoppingmall.user.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST,value = "/addressDelete.do")
public class AddressDeleteController implements BaseController {
    private AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        String addressName = req.getParameter("address_name");

        if(userId != null && addressName != null) {
            addressService.deleteAddressByUserIdAndAddressName(userId, addressName);
        }
        return "redirect:/address.do";
    }
}
