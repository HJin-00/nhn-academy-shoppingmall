package com.nhnacademy.shoppingmall.controller.userpage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

@RequestMapping(method = RequestMapping.Method.POST,value = "/updateAction.do")
public class UserUpdatePostController implements BaseController {

    private final UserService userService= new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(session == null) {
            return "redirect:/login.do";
        }

        User user = (User) session.getAttribute("user");
        if(user == null) {
            return "redirect:/login.do";
        }

        String userName = req.getParameter("user_name");
        String password = req.getParameter("user_password");
        String userBirth = req.getParameter("user_birth");
        User.Auth userAuth = User.Auth.valueOf(req.getParameter("user_auth"));

        int userPoint = user.getUserPoint();
        String userId = user.getUserId();
        LocalDateTime createdAt = user.getCreatedAt();
        LocalDateTime LatestLoginAt = user.getLatestLoginAt();

        user = new User(userId,userName,password,userBirth,userAuth,userPoint,createdAt,LatestLoginAt);
        userService.updateUser(user);
        session.setAttribute("user", user);

        return "mypage/detail/userInfo";
    }
}
