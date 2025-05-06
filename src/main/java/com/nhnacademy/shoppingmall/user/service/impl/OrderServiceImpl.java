package com.nhnacademy.shoppingmall.user.service.impl;


import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.*;
import com.nhnacademy.shoppingmall.user.repository.*;
import com.nhnacademy.shoppingmall.user.service.OrderService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RequestChannel requestChannel;
    private final PointRepository pointRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderDetailRepository orderDetailRepository,
                            CartItemRepository cartItemRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository,
                            RequestChannel requestChannel,
                            PointRepository pointRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.requestChannel = requestChannel;
        this.pointRepository = pointRepository;
    }


    @Override
    public void placeOrder(String userId, List<CartItem> cartItems, int usedPoint) {
        try {
            DbConnectionThreadLocal.initialize();

            validateCartItems(cartItems);
            int totalAmount = calculateTotal(cartItems);
            User user = getUser(userId);
            validateUserPoint(user, usedPoint);

            int finalAmount = totalAmount - usedPoint;
            int earnPoint = (int) (usedPoint * 0.1);

            int orderId = createOrder(userId, usedPoint, earnPoint);
            saveOrderDetails(orderId, cartItems);

            userRepository.updatePointByUserId(userId, -usedPoint);
            if (usedPoint > 0) {
                Point usePoint = new Point(userId, -usedPoint, Point.PointDescription.USE, LocalDateTime.now());
                pointRepository.save(usePoint);
            }

            try {
                requestChannel.addRequest(new PointChannelRequest(userId, earnPoint, userRepository, pointRepository));
            } catch (InterruptedException e) {
                log.error("포인트 적립 요청 실패", e);
            }

            cartItemRepository.deleteByCartId(cartItems.get(0).getCartId());

        } catch (Exception e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException(e);
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }

    private void validateCartItems(List<CartItem> cartItems) {
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("상품 재고가 부족합니다: " + product.getProductId());
            }
        }
    }

    private int calculateTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToInt(item -> productRepository.findById(item.getProductId())
                        .map(product -> product.getPrice() * item.getQuantity())
                        .orElse(0))
                .sum();
    }

    private User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    private void validateUserPoint(User user, int usedPoint) {
        if (user.getUserPoint() < usedPoint) {
            throw new RuntimeException("포인트가 부족합니다.");
        }
    }

    private int createOrder(String userId, int usedPoint, int earnPoint) {
        Order order = new Order();
        order.setUserId(userId);
        order.setUsedPoint(usedPoint);
        order.setEarnPoint(earnPoint);
        order.setOrderStatus("ORDERED");
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    private void saveOrderDetails(int orderId, List<CartItem> cartItems) {
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId()).get();

            OrderDetail detail = new OrderDetail();
            detail.setOrderId(orderId);
            detail.setProductId(item.getProductId());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(product.getPrice());
            orderDetailRepository.save(detail);

            productRepository.updateStock(product.getProductId(), product.getStock() - item.getQuantity());
        }
    }

    @Override
    public Page<Order> getPagedOrderList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Order> orders = orderRepository.findPage(offset, pageSize);
        long total = orderRepository.count();
        return new Page<>(orders, total);
    }

}

