package com.nhnacademy.shoppingmall.controller.userpage.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(method = RequestMapping.Method.GET, value = "/productDetail.do")
public class ProductDetailController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(), new ProductCategoryRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("productId"));
        Cookie[] cookies = req.getCookies();
        String value = String.valueOf(productId);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("viewedProducts".equals(cookie.getName())) {

                    String decoded = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);


                    List<Integer> ids = Arrays.stream(decoded.split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    ids.removeIf(id -> id == productId);
                    ids.add(0, productId);

                    if (ids.size() > 5) {
                        ids = ids.subList(0, 5);
                    }

                    value = ids.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                }
            }
        }


        String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);

        Cookie viewedCookie = new Cookie("viewedProducts", encodedValue);
        viewedCookie.setPath("/");
        viewedCookie.setMaxAge(60 * 60 * 24 * 7); // 7일
        resp.addCookie(viewedCookie);


        Product product = productService.findById(productId).orElseThrow(()->new RuntimeException("상품이 존재하지 않습니다."));
        req.setAttribute("product", product);
        return "shop/product/detail";
    }
}
