<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 5. 1.
  Time: 오후 3:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cfmt" uri="http://nhnacademy.com/cfmt" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="container">
  <h2 class="mt-4 mb-3">상품 목록</h2>

  <table class="table table-bordered text-center">
    <thead class="table-light">
    <tr>
      <th>ID</th>
      <th>상품명</th>
      <th>가격</th>
      <th>썸네일</th>
      <th>등록일</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productList}">
      <tr>
        <td>${product.productId}</td>
        <td>${product.productName}</td>
        <td><fmt:formatNumber value="${product.price}" groupingUsed="true"/> 원</td>
        <td>
          <img src="${empty product.thumbnailImage ? '/resources/no-image.png' : product.thumbnailImage}" width="80">
        </td>
        <td>${cfmt:formatDate(product.createdAt,' yyyy-MM-dd HH:mm:ss')}</td>
        <td>
          <div class="d-flex justify-content-center gap-2">
            <a href="/admin/editProduct.do?productId=${product.productId}" class="btn btn-primary btn-sm">수정</a>
          <form action="/admin/deleteProduct.do" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
            <input type="hidden" name="productId" value="${product.productId}" />
            <button type="submit" class="btn btn-danger btn-sm">삭제</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
