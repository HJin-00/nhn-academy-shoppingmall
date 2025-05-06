package com.nhnacademy.shoppingmall.controller.userpage.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.repository.impl.CartItemRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.CartItemService;
import com.nhnacademy.shoppingmall.user.service.impl.CartItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/delete.do")
public class CartItemDeleteController implements BaseController {

    private final CartItemService cartItemService = new CartItemServiceImpl(new CartItemRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int cartItemId = Integer.parseInt(req.getParameter("cartItemId"));

        cartItemService.removeCartItem(cartItemId);

        return "redirect:/cart/view.do";
    }
}
