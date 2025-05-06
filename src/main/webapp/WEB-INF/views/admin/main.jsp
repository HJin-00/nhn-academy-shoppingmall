<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 5. 1.
  Time: 오후 2:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h2 class="mt-4 mb-3">관리자 메인 페이지</h2>

    <div class="alert alert-info">
        관리자님, 쇼핑몰 관리에 오신 것을 환영합니다!
    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-primary text-white">상품 관리</div>
                <div class="card-body">
                    <p>상품을 등록하거나 목록을 확인할 수 있습니다.</p>
                    <a href="/admin/productList.do" class="btn btn-outline-primary btn-sm">상품 목록 보기</a>
                    <a href="/admin/addProduct.do" class="btn btn-outline-success btn-sm">상품 등록</a>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-success text-white">카테고리 관리</div>
                <div class="card-body">
                    <p>카테고리를 추가하거나 목록을 확인할 수 있습니다.</p>
                    <a href="/admin/addCategory.do" class="btn btn-outline-success btn-sm">카테고리 등록</a>
                    <a href="/admin/categoryList.do" class="btn btn-outline-dark btn-sm">카테고리 목록</a>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-secondary text-white">주문 관리</div>
                <div class="card-body">
                    <p>고객의 주문 내역을 확인하고 관리할 수 있습니다.</p>
                    <a href="/admin/orderList.do" class="btn btn-outline-secondary btn-sm">주문 목록 보기</a>
                </div>
            </div>
        </div>
    </div>
</div>
