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
<html>
<head>
    <title>Point History</title>
</head>
<body>
<h2>Point History</h2>
<table border="1">
    <tr>
        <th>유저 아이디</th>
        <th>&nbsp;&nbsp;&nbsp;&nbsp;변동 포인트&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>비고</th>
        <th>포인트 내역 일시</th>
    </tr>
    <c:forEach var="pointHistory" items="${pointHistoryList}">
        <tr>
            <td>${pointHistory.userId}</td>
            <td>${pointHistory.pointAction}</td>
            <td>${pointHistory.pointdescription}</td>
            <td>${cfmt:formatDate(pointHistory.pointHistoryDate,'yyyy-MM-dd HH:mm:ss')}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
