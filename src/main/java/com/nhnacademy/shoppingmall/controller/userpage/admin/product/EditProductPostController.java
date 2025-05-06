package com.nhnacademy.shoppingmall.controller.userpage.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/editProductAction.do")
public class EditProductPostController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(), new ProductCategoryRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("product_id"));

        Product existingProduct = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(req.getParameter("product_name"));
        product.setPrice(Integer.parseInt(req.getParameter("price")));
        product.setStock(Integer.parseInt(req.getParameter("stock")));
        product.setThumbnailImage(req.getParameter("thumbnail_image"));
        product.setDetailImage(req.getParameter("detail_image"));
        product.setProductDescription(req.getParameter("product_description"));
        product.setCreatedAt(existingProduct.getCreatedAt());
        product.setUpdatedAt(LocalDateTime.now());

        productService.update(product);
        return "redirect:/admin/productList.do";
    }
}
