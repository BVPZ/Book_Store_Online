<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 21/03/2026
  Time: 11:13 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jsp file</title>
</head>
<body>
    <h1>HELLO WORLD</h1>
    <h2>bạn đang ở trang giao diện chính <br>
        Nhấn nút để vào trang login
    </h2>
    <form action="login" method="get">
        <input type="submit" value="enter">
    </form>
    <form action="connectDB" method="get">
        <input type="submit" value="connect to db">
    </form>
</body>
</html>
