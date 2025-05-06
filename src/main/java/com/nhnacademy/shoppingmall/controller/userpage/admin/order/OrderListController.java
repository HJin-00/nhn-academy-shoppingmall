package com.nhnacademy.shoppingmall.controller.userpage.admin.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.user.domain.Order;
import com.nhnacademy.shoppingmall.user.repository.*;
import com.nhnacademy.shoppingmall.user.repository.impl.*;
import com.nhnacademy.shoppingmall.user.service.OrderService;
import com.nhnacademy.shoppingmall.user.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET,value = "/admin/orderList.do")
public class OrderListController implements BaseController {
    private static final int PAGE_SIZE = 10;
    private final CartItemRepository cartItemRepository = new CartItemRepositoryImpl();
    private final CartRepository cartRepository = new CartRepositoryImpl();
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final OrderDetailRepository orderDetailRepository = new OrderDetailRepositoryImpl();
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final PointRepository pointRepository = new PointRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
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
        int page = 1;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        Page<Order> orderPage = orderService.getPagedOrderList(page, PAGE_SIZE);

        req.setAttribute("orderPage", orderPage);
        req.setAttribute("currentPage", page);

        return "/admin/order/list";
    }
}
