package com.nhnacademy.shoppingmall.controller.userpage.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.repository.impl.CartItemRepositoryImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.CartService;
import com.nhnacademy.shoppingmall.user.service.impl.CartServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/update.do")
public class CartItemUpdateController implements BaseController {

    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl(), new CartItemRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        cartService.updateCartItemQuantity(cartItemId, quantity);
        return "redirect:/cart/view.do";
    }
}
