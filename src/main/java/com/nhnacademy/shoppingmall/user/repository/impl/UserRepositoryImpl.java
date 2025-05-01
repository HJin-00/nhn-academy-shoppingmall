package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*todo#3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id=? and user_password =?";

        log.debug("sql:{}", sql);
        ResultSet rs = null;
        try (
                PreparedStatement psmt = connection.prepareStatement(sql);

        ) {
            psmt.setString(1, userId);
            psmt.setString(2, userPassword);
            rs = psmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_birth"),
                        User.Auth.valueOf(rs.getString("user_auth")),
                        rs.getInt("user_point"),
                        Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                );
                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        String sql = "select*from users where user_id=?";
        log.debug("sql:{}", sql);
        ResultSet rs = null;
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (
                PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, userId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_birth"),
                        User.Auth.valueOf(rs.getString("user_auth")),
                        rs.getInt("user_point"),
                        Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.
        String sql = "insert into users set user_id=?,user_name=?,user_password=?,user_birth=?,user_auth=?,user_point=?,created_at=?";
        log.debug("sql:{}", sql);
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (
                PreparedStatement psmt = connection.prepareStatement(sql);) {
            psmt.setString(1, user.getUserId());
            psmt.setString(2, user.getUserName());
            psmt.setString(3, user.getUserPassword());
            psmt.setString(4, user.getUserBirth());
            psmt.setString(5, user.getUserAuth().name());
            psmt.setInt(6, user.getUserPoint());
            psmt.setTimestamp(7, user.getCreatedAt() != null ? Timestamp.valueOf(user.getCreatedAt()) : null);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.
        String sql = "delete from users where user_id=?";
        log.debug("sql:{}", sql);
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (
                PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(User user) {
        //todo#3-5 회원수정, executeUpdate()을 반환합니다.
        String sql = "update users set user_name=?,user_password=?,user_birth=?,user_auth=?,user_point=?,created_at=? where user_id=?";
        log.debug("sql:{}", sql);
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (
                PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, user.getUserName());
            psmt.setString(2, user.getUserPassword());
            psmt.setString(3, user.getUserBirth());
            psmt.setString(4, user.getUserAuth().name());
            psmt.setInt(5, user.getUserPoint());
            psmt.setTimestamp(6, user.getCreatedAt() != null ? Timestamp.valueOf(user.getCreatedAt()) : null);
            psmt.setString(7, user.getUserId());
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        //todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        String sql = "update users set latest_login_at=? where user_id=?";
        log.debug("sql:{}", sql);
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (
                PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setTimestamp(1, latestLoginAt != null ? Timestamp.valueOf(latestLoginAt) : null);
            psmt.setString(2, userId);
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "select count(*) from users where user_id=?";
        log.debug("sql:{}", sql);
        ResultSet rs = null;
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (
                PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return 0;
    }

    @Override
    public void updatePointByUserId(String userId, int changePoint) {
        String sql = "UPDATE users SET user_point = user_point + ? WHERE user_id = ?";
        try (Connection conn = DbConnectionThreadLocal.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, changePoint);
            psmt.setString(2, userId);
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("회원 포인트 갱신 실패", e);
        }


    }
}
