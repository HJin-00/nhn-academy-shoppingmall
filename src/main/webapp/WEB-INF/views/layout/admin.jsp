<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>관리자 페이지</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<!-- 상단 네비게이션 바 -->
<header class="p-3 bg-dark text-white mb-4">
    <div class="container d-flex flex-wrap align-items-center justify-content-between">
        <a href="/admin/index.do" class="text-white text-decoration-none">
            <h4>Admin Panel</h4>
        </a>

        <c:choose>
            <c:when test="${pageContext.session != null and not empty sessionScope.user}">
                <div>
                    <span>${sessionScope.user.userName}님</span>
                    <span>(보유 포인트: <fmt:formatNumber value="${sessionScope.user.userPoint}" groupingUsed="true"/> P)</span>
                    <a href="/mypage.do" class="btn btn-outline-light btn-sm ms-2">마이페이지</a>
                    <a href="/logout.do" class="btn btn-danger btn-sm ms-2">로그아웃</a>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <a class="btn btn-outline-light btn-sm me-2" href="/login.do">로그인</a>
                    <a class="btn btn-warning btn-sm" href="/signup.do">회원가입</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</header>

<!-- 본문 레이아웃 -->
<div class="container">
    <div class="row">

        <!-- 좌측 관리자 메뉴 -->
        <div class="col-md-3">
            <ul class="nav nav-pills flex-column">
                <li class="nav-item">
                    <a class="nav-link" href="/admin/productList.do">상품 목록</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/addProduct.do">상품 추가</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/orderList.do">주문 관리</a>
                </li>
            </ul>
        </div>

        <!-- 메인 콘텐츠 영역 -->
        <div class="col-md-9">
            <jsp:include page="${layout_content_holder}" />
        </div>

    </div>
</div>

</body>
</html>
