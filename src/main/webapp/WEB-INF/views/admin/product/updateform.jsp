<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 5. 6.
  Time: 오후 3:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-4">
  <h2>상품 수정</h2>

  <form action="/admin/editProductAction.do" method="post">
    <input type="hidden" name="product_id" value="${product.productId}" />

    <div class="mb-3">
      <label class="form-label">상품명</label>
      <input type="text" class="form-control" name="product_name" value="${product.productName}" required>
    </div>

    <div class="mb-3">
      <label class="form-label">가격</label>
      <input type="number" class="form-control" name="price" value="${product.price}" required>
    </div>

    <div class="mb-3">
      <label class="form-label">수량</label>
      <input type="number" class="form-control" name="stock" value="${product.stock}" required>
    </div>

    <div class="mb-3">
      <label class="form-label">썸네일 이미지 경로</label>
      <input type="text" class="form-control" name="thumbnail_image" value="${product.thumbnailImage}">
    </div>

    <div class="mb-3">
      <label class="form-label">상세 이미지 경로</label>
      <input type="text" class="form-control" name="detail_image" value="${product.detailImage}">
    </div>

    <div class="mb-3">
      <label class="form-label">상품 설명</label>
      <textarea class="form-control" name="product_description" rows="4">${product.productDescription}</textarea>
    </div>

    <button type="submit" class="btn btn-primary">수정 완료</button>
  </form>
</div>

