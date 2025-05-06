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
                    // 문자열 리스트 → int 리스트로 파싱
                    List<Integer> ids = Arrays.stream(cookie.getValue().split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    ids.removeIf(id -> id == productId); // 중복 제거
                    ids.add(0, productId); // 최신 ID 맨 앞에 추가

                    if (ids.size() > 5) {
                        ids = ids.subList(0, 5); // 최대 5개 유지
                    }

                    // 다시 문자열로 조합
                    value = ids.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                }
            }
        }

        Cookie viewedCookie = new Cookie("viewedProducts", value);
        viewedCookie.setPath("/");
        viewedCookie.setMaxAge(60 * 60 * 24 * 7); // 일주일
        resp.addCookie(viewedCookie);

        Product product = productService.findById(productId).orElseThrow(()->new RuntimeException("상품이 존재하지 않습니다."));
        req.setAttribute("product", product);
        return "shop/product/detail";
    }
}
