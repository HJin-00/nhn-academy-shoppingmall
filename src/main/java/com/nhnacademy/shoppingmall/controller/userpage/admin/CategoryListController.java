package com.nhnacademy.shoppingmall.controller.userpage.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.CategoryRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/categoryList.do")
public class CategoryListController implements BaseController {
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Category> categories = categoryRepository.findAll();
        req.setAttribute("categories", categories);
        return "admin/category/list";
    }
}
