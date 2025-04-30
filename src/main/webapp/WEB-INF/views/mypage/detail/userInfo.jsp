<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 4. 30.
  Time: 오후 1:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cfmt" uri="http://nhnacademy.com/cfmt" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>


<div class="container my-5">
    <div class="card shadow rounded">
        <div class="card-header text-center bg-primary text-white">
            <h2 class="mb-0">유저 정보</h2>
        </div>
        <div class="card-body">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <table class="table table-bordered">
                        <tbody>
                        <tr>
                            <th class="bg-light text-end" width="30%">아이디</th>
                            <td>${sessionScope.user.userId}</td>
                        </tr>
                        <tr>
                            <th class="bg-light text-end">이름</th>
                            <td>${sessionScope.user.userName}</td>
                        </tr>
                        <tr>
                            <th class="bg-light text-end">생년월일</th>
                            <td>${sessionScope.user.userBirth}</td>
                        </tr>
                        <tr>
                            <th class="bg-light text-end">권한</th>
                            <td>${sessionScope.user.userAuth}</td>
                        </tr>
                        <tr>
                            <th class="bg-light text-end">포인트</th>
                            <td><fmt:formatNumber value="${sessionScope.user.userPoint}" groupingUsed="true"/> P</td>
                        </tr>
                        <tr>
                            <th class="bg-light text-end">가입일</th>
                            <td>${cfmt:formatDate(sessionScope.user.createdAt,'yyyy-MM-dd HH:mm:ss')}</td>
                        </tr>
                        <tr>
                            <th class="bg-light text-end">최근 접속일</th>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty sessionScope.user.latestLoginAt}">
                                        ${cfmt:formatDate(sessionScope.user.latestLoginAt,'yyyy-MM-dd HH:mm:ss')}
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                    <div class="text-center mt-4">
                        <a class="btn btn-warning px-4" href="/update.do">정보 수정</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
