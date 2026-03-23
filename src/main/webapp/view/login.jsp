<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - Nhà Sách Tri Thức</title>
    <!-- Thêm font Google Inter -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_style.css">
</head>

<body>

<div class="login-container">
    <div class="logo">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
            <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path>
            <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path>
        </svg>
    </div>

    <h2>Nhà Sách Tri Thức</h2>
    <div class="subtitle">Đăng nhập để tiếp tục</div>

    <form action="home" method="POST">
        <div class="input-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="example@email.com" required>
        </div>

        <div class="input-group">
            <label for="password">Mật khẩu</label>
            <input type="password" id="password" name="password" placeholder="••••••••" required>
        </div>

        <button type="submit" class="submit-btn">Đăng nhập</button>
    </form>

    <div class="register-link">
        Chưa có tài khoản? <a href="${pageContext.request.contextPath}/view/register.jsp">Đăng ký ngay</a>
    </div>

    <div class="demo-text">
        Demo: Nhập bất kỳ email và mật khẩu để đăng nhập
    </div>
</div>

</body>

</html>