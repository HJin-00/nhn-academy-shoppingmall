package com.nhnacademy.shoppingmall.controller.userpage.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.CartItemRepositoryImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.CartService;

import com.nhnacademy.shoppingmall.user.service.impl.CartServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/add.do")
public class CartAddController implements BaseController {

    private final CartService cartService = new CartServiceImpl(
            new CartRepositoryImpl(),
            new CartItemRepositoryImpl()
    );

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login.do";
        }

        User user = (User) session.getAttribute("user");


        int productId = Integer.parseInt(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));


        cartService.addItemToCart(user.getUserId(), productId, quantity);


        return "redirect:/cart/view.do";
    }
}
