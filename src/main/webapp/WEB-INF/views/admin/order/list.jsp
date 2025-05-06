<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 5. 6.
  Time: 오후 8:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cfmt" uri="http://nhnacademy.com/cfmt" %>

<div class="container mt-4">
  <h2>주문 목록</h2>

  <table class="table table-bordered text-center">
    <thead class="table-light">
    <tr>
      <th>주문 ID</th>
      <th>사용자 ID</th>
      <th>사용 포인트</th>
      <th>적립 포인트</th>
      <th>주문 상태</th>
      <th>주문 일자</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${orderPage.content}">
      <tr>
        <td>${order.orderId}</td>
        <td>${order.userId}</td>
        <td>${order.usedPoint}</td>
        <td>${order.earnPoint}</td>
        <td>${order.orderStatus}</td>
        <td>${cfmt:formatDate(order.createdAt,'yyyy-MM-dd HH:mm:ss')}</td>

      </tr>
    </c:forEach>
    </tbody>
  </table>

  <!-- 페이징 처리 -->
  <nav>
    <ul class="pagination justify-content-center">
      <c:forEach var="i" begin="1" end="${orderPage.totalCount / 10 + (orderPage.totalCount % 10 == 0 ? 0 : 1)}">
        <li class="page-item ${i == param.page ? 'active' : ''}">
          <a class="page-link" href="/admin/orderList.do?page=${i}">${i}</a>
        </li>
      </c:forEach>
    </ul>
  </nav>
</div>
