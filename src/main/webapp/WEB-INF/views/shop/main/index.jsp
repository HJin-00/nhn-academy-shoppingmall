<%--
  Created by IntelliJ IDEA.
  User: nhn
  Date: 2023/11/08
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="cfmt" uri="http://nhnacademy.com/cfmt" %>

<div class="container">
    <div class="row">
        <!-- 최근 본 상품 사이드바 -->
        <div class="col-md-3">
            <h5 class="mb-3">최근 본 상품</h5>
            <c:forEach var="recent" items="${recentViewedProducts}">
                <div class="card mb-3" style="width: 100%;">
                    <a href="/productDetail.do?productId=${recent.productId}" style="text-decoration: none; color: inherit;">
                        <img src="${empty recent.thumbnailImage ? '/resources/no-image.png' : recent.thumbnailImage}"
                             class="card-img-top" style="max-height: 150px; object-fit: cover;" alt="${recent.productName}">
                        <div class="card-body p-2">
                            <strong class="card-title" style="font-size: 0.95rem;">${recent.productName}</strong><br/>
                            <fmt:formatNumber value="${recent.price}" type="currency"/>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>

        <!-- 일반 상품 목록 -->
        <div class="col-md-9">
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                <c:forEach var="product" items="${products}">
                    <c:set var="imgPath" value="${empty product.thumbnailImage ? '/resources/no-image.png' : product.thumbnailImage}" />
                    <div class="col">
                        <div class="card shadow-sm">
                            <img class="card-img-top" width="100%" height="200" src="${imgPath}" alt="${product.productName}" />
                            <div class="card-body">
                                <p class="card-text">
                                    <strong>${product.productName}</strong><br />
                                    <fmt:formatNumber value="${product.price}" type="currency" />
                                </p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <a href="/productDetail.do?productId=${product.productId}" class="btn btn-sm btn-outline-secondary">View</a>
                                        <c:if test="${not empty sessionScope.user and sessionScope.user.userAuth eq 'ROLE_ADMIN'}">
                                            <a href="/admin/editProduct.do?productId=${product.productId}" class="btn btn-sm btn-outline-warning ms-2">Edit</a>
                                        </c:if>
                                    </div>
                                    <small class="text-muted">
                                            ${cfmt:formatDate(product.createdAt, 'yyyy-MM-dd HH:mm:ss')}
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center mt-4">
        <c:forEach begin="1" end="${(totalCount / pageSize) + (totalCount % pageSize > 0 ? 1 : 0)}" var="p">
            <li class="page-item ${p == currentPage ? 'active' : ''}">
                <a class="page-link" href="/index.do?page=${p}">${p}</a>
            </li>
        </c:forEach>
    </ul>
</nav>


