<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cfmt" uri="http://nhnacademy.com/cfmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Address Form</title>

    <!-- Bootstrap 5 CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <script>
        function showSaveSuccess() {
            alert('저장이 완료되었습니다!');
        }

        function showDeleteSuccess() {
            alert('삭제가 완료되었습니다!');
        }
    </script>
</head>
<body>
<div class="container my-5">
    <div class="row gx-5">
        <!-- 주소 등록 폼 -->
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">주소 등록</div>
                <div class="card-body">
                    <form action="/addressAdd.do" method="post" onsubmit="showSaveSuccess()">
                        <input type="hidden" name="user_id" value="${sessionScope.user.userId}">

                        <div class="mb-3">
                            <label for="address_name" class="form-label">주소 이름 (예: 집, 회사 등):</label>
                            <input type="text" id="address_name" name="address_name" class="form-control" value="${address.addressName}">
                        </div>

                        <div class="mb-3">
                            <label for="address_detail" class="form-label">상세주소:</label>
                            <input type="text" id="address_detail" name="address_detail" class="form-control" value="${address.addressDetail}">
                        </div>

                        <button type="submit" class="btn btn-success w-100">저장</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- 주소 목록 테이블 -->
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header bg-secondary text-white">주소 목록</div>
                <div class="card-body">
                    <table class="table table-bordered text-center align-middle">
                        <thead class="table-light">
                        <tr>
                            <th>주소 이름</th>
                            <th>상세 주소</th>
                            <th>등록일</th>
                            <th>삭제</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="address" items="${addressList}">
                            <tr>
                                <td>${address.addressName}</td>
                                <td>${address.addressDetail}</td>
                                <td>${cfmt:formatDate(address.createdAt, 'yyyy-MM-dd HH:mm:ss')}</td>
                                <td>
                                    <form action="/addressDelete.do" method="post"
                                          onsubmit="return confirm('정말 삭제하시겠습니까?')"
                                          style="display:inline;">
                                        <input type="hidden" name="user_id" value="${sessionScope.user.userId}">
                                        <input type="hidden" name="address_name" value="${address.addressName}">
                                        <button type="submit" class="btn btn-sm btn-outline-danger">삭제</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>

</div>

<!-- Bootstrap 5 JS CDN -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
