<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 5. 4.
  Time: 오후 4:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-4">
  <h2>카테고리 목록</h2>

  <table class="table table-bordered">
    <thead>
    <tr>
      <th>카테고리 ID</th>
      <th>카테고리 이름</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="category" items="${categories}">
      <tr>
        <td>${category.categoryId}</td>
        <td>${category.categoryName}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>