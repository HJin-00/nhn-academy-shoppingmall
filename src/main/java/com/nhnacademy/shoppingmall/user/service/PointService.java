package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.Point;

import java.awt.print.Pageable;

public interface PointService {
    int save(Point point);
    Page<Point> findPageByUserId(String userId, int page, int pageSize);
    long countByUserId(String userId);
    int getTotalPointByUserId(String userId);
    void earnOrUsePoint(Point point);
}
