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

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/addProductAction.do")
public class AddProductPostController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(),new ProductCategoryRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("product_name");
        int price = Integer.parseInt(req.getParameter("price"));
        int stock = Integer.parseInt(req.getParameter("stock"));

        String thumbnail = req.getParameter("thumbnail_image");
        if(thumbnail == null || thumbnail.isBlank()) {
            thumbnail = "/resources/no-image.png";
        }
        String detailImage = req.getParameter("detail_image");
        if(detailImage == null || detailImage.isBlank()) {
            detailImage = "/resources/no-image.png";
        }

        String description = req.getParameter("product_description");

        int categoryId = Integer.parseInt(req.getParameter("category_id"));

        Product product = new Product();
        product.setProductName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setThumbnailImage(thumbnail);
        product.setDetailImage(detailImage);
        product.setProductDescription(description);
        product.setCreatedAt(LocalDateTime.now());

        productService.addProduct(product, categoryId);
        return "redirect:/admin/productList.do";


    }
}
