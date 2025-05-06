<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 5. 6.
  Time: 오후 4:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="cfmt" uri="http://nhnacademy.com/cfmt" %>

<div class="container">
  <h2 class="mb-4">관리자 상품 목록</h2>

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
                <a href="/admin/editProduct.do?productId=${product.productId}" class="btn btn-sm btn-outline-warning ms-2">Edit</a>
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
<nav aria-label="Page navigation">
  <ul class="pagination justify-content-center mt-4">
    <c:forEach begin="1" end="${(totalCount / pageSize) + (totalCount % pageSize > 0 ? 1 : 0)}" var="p">
      <li class="page-item ${p == currentPage ? 'active' : ''}">
        <a class="page-link" href="/index.do?page=${p}">${p}</a>
      </li>
    </c:forEach>
  </ul>
</nav>