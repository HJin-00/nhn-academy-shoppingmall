
<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 5. 1.
  Time: 오후 3:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-4">
    <h2>상품 등록</h2>

    <form action="/admin/addProductAction.do" method="post">
        <div class="mb-3">
            <label for="productName" class="form-label">상품명</label>
            <input type="text" class="form-control" id="productName" name="product_name" required>
        </div>
        <div class="mb-3">
            <label for="category" class="form-label">카테고리 선택</label>
            <select class="form-select" id="category" name="category_id" required>
                <option value="">-- 카테고리 선택 --</option>
                <c:forEach var="category" items="${categoryList}">
                    <option value="${category.categoryId}">${category.categoryName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label for="stock" class="form-label">재고 수량</label>
            <input type="number" class="form-control" id="stock" name="stock" required min="0">
        </div>

        <div class="mb-3">
            <label for="price" class="form-label">가격</label>
            <input type="number" class="form-control" id="price" name="price" required>
        </div>

        <div class="mb-3">
            <label for="thumbnail" class="form-label">썸네일 이미지 경로</label>
            <input type="text" class="form-control" id="thumbnail" name="thumbnail_image">
        </div>

        <div class="mb-3">
            <label for="detail" class="form-label">상세 이미지 경로</label>
            <input type="text" class="form-control" id="detail" name="detail_image">
        </div>

        <div class="mb-3">
            <label for="desc" class="form-label">상품 설명</label>
            <textarea class="form-control" id="desc" name="product_description" rows="4" required></textarea>
        </div>

        <button type="submit" class="btn btn-primary">등록하기</button>
    </form>
</div>

