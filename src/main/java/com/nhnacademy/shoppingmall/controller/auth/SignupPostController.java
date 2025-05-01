package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.Point;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.impl.PointRepositoryImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.PointService;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.PointServiceImpl;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/signupAction.do")
public class SignupPostController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final PointService pointService = new PointServiceImpl(new PointRepositoryImpl(), new UserRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String userId = req.getParameter("user_id");
            String userName = req.getParameter("user_name");
            String userPassword = req.getParameter("user_password");
            String userBirth = req.getParameter("user_birth");
            User.Auth userAuth = User.Auth.valueOf(req.getParameter("user_auth"));
            int points = 1_000_000;
            User user = new User(userId, userName, userPassword, userBirth, userAuth, points, LocalDateTime.now(), null);

            log.debug("user: {} 생성완료", userId);

            if (Objects.isNull(user)) {
                throw new RuntimeException("user 생성 실패");
            }

            userService.saveUser(user);
            Point joinBonus = new Point(
                    userId,points, Point.PointDescription.EARN,LocalDateTime.now()
            );
            pointService.save(joinBonus);
            log.debug("{} 생성완료", user.getUserId());

            return "shop/main/index";
        }catch (UserAlreadyExistsException e) {
            req.getSession(true).setAttribute("signupError", "이미 존재하는 아이디입니다.");
            return "redirect:/signup.do";
        }catch (Exception e) {
            req.getSession(true).setAttribute("signupError", "회원가입 중 오류가 발생했습니다.");
            return "redirect:/signup.do";
        }
    }
}
