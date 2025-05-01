package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.Point;
import com.nhnacademy.shoppingmall.user.repository.PointRepository;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.service.PointService;

public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    public PointServiceImpl(PointRepository pointRepository, UserRepository userRepository) {
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @Override
    public int save(Point point) {
        return pointRepository.save(point);
    }

    @Override
    public Page<Point> findPageByUserId(String userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return pointRepository.findPageByUserId(userId, offset, pageSize);
    }

    @Override
    public long countByUserId(String userId) {
        return pointRepository.countByUserId(userId);
    }

    @Override
    public int getTotalPointByUserId(String userId) {
        return pointRepository.getTotalPointByUserId(userId);
    }

    @Override
    public void earnOrUsePoint(Point point) {
        int saved = pointRepository.save(point);
        if (saved == 1) {
            userRepository.updatePointByUserId(point.getUserId(), point.getChangePoint());
        } else {
            throw new RuntimeException("포인트 적립/사용 내역 저장 실패");
        }
    }
}
