package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.Point;
import com.nhnacademy.shoppingmall.user.repository.PointRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PointRepositoryImpl implements PointRepository {


    @Override
    public int save(Point point) {
        String sql = "insert into point_history (user_id,change_point,point_description, created_at) values (?,?,?,?)";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, point.getUserId());
            psmt.setInt(2, point.getChangePoint());
            psmt.setString(3, point.getPointDescription().getDescription());
            psmt.setTimestamp(4, Timestamp.valueOf(point.getCreatedAt()));
            return psmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Point> findByUserId(String userId) {
        String sql = "select * from point_history where user_id = ? ORDER BY created_at DESC";
        List<Point> points = new ArrayList<>();
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery();) {
            psmt.setString(1,userId);
            while (rs.next()){
                Point point = new Point();
                point.setUserId(rs.getString("user_id"));
                point.setChangePoint(rs.getInt("change_point"));
                point.setPointDescription(Point.PointDescription.from(rs.getString("point_description")));
                point.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                points.add(point);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return points;
    }

    @Override
    public Page<Point> findPageByUserId(String userId, int offset, int limit) {
        String sql = "SELECT * FROM point_history WHERE user_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?";
        List<Point> points = new ArrayList<>();
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, userId);
            psmt.setInt(2, limit);
            psmt.setInt(3, offset);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    Point point = new Point();
                    point.setUserId(rs.getString("user_id"));
                    point.setChangePoint(rs.getInt("change_point"));
                    point.setPointDescription(Point.PointDescription.from(rs.getString("point_description")));
                    point.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    points.add(point);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        long total = countByUserId(userId);
        return new Page<>(points, total);
    }

    @Override
    public long countByUserId(String userId) {
        String sql = "SELECT COUNT(*) FROM point_history WHERE user_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, userId);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }


    @Override
    public int getTotalPointByUserId(String userId) {
        String sql = "SELECT SUM(change_point) FROM point_history WHERE user_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, userId);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1); // null이면 0 반환
            }

        } catch (SQLException e) {
            throw new RuntimeException("총 포인트 계산 실패", e);
        }

        return 0;
    }
}
