package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.Point;
import com.nhnacademy.shoppingmall.user.repository.PointRepository;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class PointChannelRequest extends ChannelRequest {
    private final String userId;
    private final int earnPoint;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    public PointChannelRequest(String userId, int earnPoint,
                               UserRepository userRepository,
                               PointRepository pointRepository) {
        this.userId = userId;
        this.earnPoint = earnPoint;
        this.userRepository = userRepository;
        this.pointRepository = pointRepository;
    }

    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();
        try {
            Point point = new Point(userId, earnPoint, Point.PointDescription.EARN, LocalDateTime.now());
            int saved = pointRepository.save(point);
            if (saved == 1) {
                userRepository.updatePointByUserId(userId, earnPoint);
                log.debug("포인트 적립 성공: {} → {}P", userId, earnPoint);
            } else {
                log.error("포인트 적립 실패 (insert 실패)");
            }
        } catch (Exception e) {
            log.error("포인트 적립 처리 중 예외 발생", e);
        } finally {
            try {
                DbConnectionThreadLocal.reset();
            }catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
