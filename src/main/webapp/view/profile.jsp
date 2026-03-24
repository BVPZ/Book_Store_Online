<%@ page import="com.bookshop.model.User" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 23/03/2026
  Time: 5:53 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Người dùng</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");

    %>
    <h1>Tên đăng nhập người dùng: <%= user.getUsername() %></h1>
</body>
</html>
