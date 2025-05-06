<%--
  Created by IntelliJ IDEA.
  User: kimhyunjin
  Date: 2025. 5. 4.
  Time: 오후 4:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container mt-4">
  <h2>카테고리 등록</h2>

  <form action="/admin/addCategoryAction.do" method="post">
    <div class="mb-3">
      <label for="categoryName" class="form-label">카테고리 이름</label>
      <input type="text" class="form-control" id="categoryName" name="category_name" required>
    </div>
    <button type="submit" class="btn btn-primary">등록</button>
  </form>
</div>

