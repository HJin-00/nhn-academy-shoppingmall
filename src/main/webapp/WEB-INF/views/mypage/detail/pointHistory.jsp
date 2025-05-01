<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 4. 30.
  Time: 오후 1:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cfmt" uri="http://nhnacademy.com/cfmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table class="table">
    <thead>
    <tr>
        <th>포인트</th>
        <th>설명</th>
        <th>일시</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="point" items="${pointPage.content}">
        <tr>
            <td>${point.changePoint}</td>
            <td>${point.pointDescription.description}</td>
            <td>${cfmt:formatDate(point.createdAt, 'yyyy-MM-dd HH:mm:ss')}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- 페이징 UI -->
<nav>
    <ul class="pagination">
        <c:forEach var="i" begin="1" end="${totalPages}">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="/pointHistory.do?page=${i}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</nav>
