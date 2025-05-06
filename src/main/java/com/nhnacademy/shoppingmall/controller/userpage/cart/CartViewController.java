package com.nhnacademy.shoppingmall.controller.userpage.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.CartItem;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.CartItemRepositoryImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.CartService;
import com.nhnacademy.shoppingmall.user.service.impl.CartServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/cart/view.do")
public class CartViewController implements BaseController {

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
        List<CartItem> cartItems = cartService.getCartItems(user.getUserId());

        req.setAttribute("cartItems", cartItems);
        return "cart/view";
    }
}
