<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 4. 30.
  Time: 오후 1:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Address Form</title>
    <script>
        function showSaveSuccess() {
            alert('저장이 완료되었습니다!');
        }

        function showDeleteSuccess() {
            alert('삭제가 완료되었습니다!');
        }
    </script>
    <style>
        .container {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
        }
        .form-container {
            width: 45%;
        }
        .address-container {
            width: 45%;
        }
        .delete-container {
            margin-top: 20px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="form-container">
        <h2>주소 등록</h2>
        <form action="/addressSave.do" method="post" onsubmit="showSaveSuccess()">
            <input type="hidden" name="user_id" value="${sessionScope.user.userId}">

            <label for="address_name">주소 이름 (예: 집, 회사 등):</label>
            <input type="text" id="address_name" name="address_name" value="${address.addressName}">
            <br>

            <label for="address_detail">상세주소:</label>
            <input type="text" id="address_detail" name="address_detail" value="${address.addressDetail}">
            <br>

            <input type="submit" value="저장">
        </form>
    </div>
    <div class="address-container">
        <h2>주소 목록</h2>
        <table>
            <thead>
            <tr>
                <th>주소이름</th>
                <th>상세주소</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="address" items="${addressList}">
                <tr>
                    <td>${address.addressName}</td>
                    <td>${address.addressDetail}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="delete-container">
    <h2>주소 삭제</h2>
    <form action="/addressDelete.do" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?')">
        <input type="hidden" name="user_id" value="${sessionScope.user.userId}">
        <label for="address_id">삭제할 주소 ID:</label>
        <input type="text" id="address_id" name="address_id" required>
        <input type="submit" value="삭제">
    </form>
</div>

</body>
</html>
