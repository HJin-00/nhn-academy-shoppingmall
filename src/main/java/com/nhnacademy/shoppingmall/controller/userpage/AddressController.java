package com.nhnacademy.shoppingmall.controller.userpage;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET,value = "/address.do")
public class AddressController implements BaseController {
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = ((User)req.getSession().getAttribute("user")).getUserId();

        List<Address> addressList = addressService.getAllAddresses();
        req.setAttribute("addressList", addressList);

        return "/mypage/detail/address";
    }
}
