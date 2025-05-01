package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.Point;

import java.awt.print.Pageable;
import java.util.List;

public interface PointRepository {

    int save(Point point);

    List<Point> findByUserId(String userId);

    Page<Point> findPageByUserId(String userId, int offset, int limit);

    int getTotalPointByUserId(String userId);

    long countByUserId(String userId);

}
