<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 5. 6.
  Time: 오후 5:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-4">
  <h2 class="mb-3">장바구니</h2>

  <c:if test="${empty cartItems}">
    <p>장바구니에 담긴 상품이 없습니다.</p>
  </c:if>

  <c:if test="${not empty cartItems}">
    <table class="table table-bordered text-center">
      <thead class="table-light">
      <tr>
        <th>상품ID</th>
        <th>상품명</th>
        <th>수량</th>
        <th>가격</th>
        <th>총합</th>
        <th>변경</th>
        <th>삭제</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${cartItems}">
        <tr>
          <td>${item.productId}</td>
          <td>${item.productName}</td>
          <td>
            <input type="number" name="quantity" value="${item.quantity}" min="1"
                   form="updateForm_${item.cartItemId}" class="form-control form-control-sm w-100"/>
          </td>
          <td><fmt:formatNumber value="${item.price}" type="currency" /></td>
          <td><fmt:formatNumber value="${item.price * item.quantity}" type="currency" /></td>
          <td>
            <form id="updateForm_${item.cartItemId}" action="/cart/update.do" method="post">
              <input type="hidden" name="cartItemId" value="${item.cartItemId}" />
              <button type="submit" class="btn btn-sm btn-outline-primary">변경</button>
            </form>
          </td>
          <td>
            <form action="/cart/delete.do" method="post" onsubmit="return confirm('삭제하시겠습니까?')">
              <input type="hidden" name="cartItemId" value="${item.cartItemId}" />
              <button type="submit" class="btn btn-sm btn-danger">삭제</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

    <div class="text-end">
      <form action="/order/checkout.do" method="post" class="d-inline-block text-end">
        <div class="mb-2">
          <label for="usedPoint" class="form-label">사용할 포인트:</label>
          <input type="number" id="usedPoint" name="usedPoint" class="form-control form-control-sm d-inline-block w-auto"
                 min="0" max="${user.userPoint}" value="0" />
          <small class="text-muted ms-2">보유 포인트: <fmt:formatNumber value="${user.userPoint}" groupingUsed="true" />P</small>
        </div>
        <button type="submit" class="btn btn-success">주문하기</button>
      </form>
    </div>
  </c:if>
</div>