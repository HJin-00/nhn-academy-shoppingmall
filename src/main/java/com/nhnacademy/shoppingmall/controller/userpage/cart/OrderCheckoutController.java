package com.nhnacademy.shoppingmall.controller.userpage.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.user.domain.Cart;
import com.nhnacademy.shoppingmall.user.domain.CartItem;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.*;
import com.nhnacademy.shoppingmall.user.repository.impl.*;
import com.nhnacademy.shoppingmall.user.service.OrderService;
import com.nhnacademy.shoppingmall.user.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.util.List;

@RequestMapping(method = RequestMapping.Method.POST,value = "/order/checkout.do")
public class OrderCheckoutController implements BaseController {
    private final CartItemRepository cartItemRepository = new CartItemRepositoryImpl();
    private final CartRepository cartRepository = new CartRepositoryImpl();
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final OrderDetailRepository orderDetailRepository = new OrderDetailRepositoryImpl();
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final PointRepository pointRepository = new PointRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login.do";
        }

        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();

        int usedPoint = 0;
        if (req.getParameter("usedPoint") != null) {
            usedPoint = Integer.parseInt(req.getParameter("usedPoint"));
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());


        RequestChannel requestChannel =
                (RequestChannel) req.getServletContext().getAttribute("requestChannel");


        OrderService orderService = new OrderServiceImpl(
                orderRepository,
                orderDetailRepository,
                cartItemRepository,
                productRepository,
                userRepository,
                requestChannel,
                pointRepository
        );

        orderService.placeOrder(userId, cartItems, usedPoint);
        DbConnectionThreadLocal.initialize();
        try {
            User updatedUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자 없음"));
            session.setAttribute("user", updatedUser);
        } finally {
            DbConnectionThreadLocal.reset();
        }

        return "redirect:/mypage.do";
    }
}

