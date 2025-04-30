<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 4. 30.
  Time: 오후 1:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1 > 마이 페이지 </h1>
<div class="row">
  <div class="col-md-4 mb-3">
    <div class="list-group">
      <a href="/userinfo.do" class="list-group-item">회원 정보 확인 및 회원 정보 수정</a>
    </div>
  </div>
  <div class="col-md-4 mb-3">
    <div class="list-group">
      <a href="/address.do" class="list-group-item">회원 주소 등록</a>
    </div>
  </div>
  <div class="col-md-4 mb-3">
    <div class="list-group">
      <a href="/withDrawal.do"
         class="list-group-item"
         onclick="return confirm('정말로 탈퇴하시겠습니까?')">회원 탈퇴</a>    </div>
  </div>
  <div class="col-md-4 mb-3">
    <div class="list-group">
      <a href="/pointHistory.do" class="list-group-item">포인트 사용 및 적립 내역</a>
    </div>
  </div>
  <c:if test="${sessionScope.user != null && sessionScope.user.userAuth == 'ROLE_ADMIN'}">
    <div class="col-md-4 mb-3">
      <div class="list-group">
        <a href="/adminPage.do" class="list-group-item">관리자 페이지 접속</a>
      </div>
    </div>
  </c:if>
</div>