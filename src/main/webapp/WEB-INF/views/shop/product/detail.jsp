<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 5. 6.
  Time: 오후 2:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-4">
  <h2>${product.productName}</h2>

  <div class="row">
    <div class="col-md-6">
      <img src="${empty product.detailImage ? '/resources/no-image.png' : product.detailImage}"
           class="img-fluid" alt="${product.productName}">
    </div>
    <div class="col-md-6">
      <p><strong>가격:</strong> <fmt:formatNumber value="${product.price}" groupingUsed="true"/> 원</p>
      <p><strong>설명:</strong></p>
      <p>${product.productDescription}</p>
      <p><strong>재고:</strong> ${product.stock} 개</p>

      <a href="/addToCart.do?productId=${product.productId}" class="btn btn-primary">장바구니에 담기</a>
    </div>
  </div>
</div>
