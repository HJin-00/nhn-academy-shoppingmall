package com.nhnacademy.shoppingmall.controller.userpage.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET,value = "/admin/admin.do")
public class AdminPageController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        return "admin/main";
    }
}
