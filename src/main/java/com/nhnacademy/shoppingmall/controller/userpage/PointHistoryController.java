package com.nhnacademy.shoppingmall.controller.userpage;


import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.Point;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.PointRepositoryImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.PointService;
import com.nhnacademy.shoppingmall.user.service.impl.PointServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.GET, value = "/pointHistory.do")
public class PointHistoryController implements BaseController {
    private final PointService pointService = new PointServiceImpl(new PointRepositoryImpl(),new UserRepositoryImpl());

    private static final int PAGE_SIZE = 10;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login.do";
        }

        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();

        int page = 1;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException | NullPointerException ignored) {}

        Page<Point> pointPage = pointService.findPageByUserId(userId, page, PAGE_SIZE);

        req.setAttribute("pointPage", pointPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", (int) Math.ceil((double) pointPage.getTotalCount() / PAGE_SIZE));

        return "/mypage/detail/pointHistory";
    }
}