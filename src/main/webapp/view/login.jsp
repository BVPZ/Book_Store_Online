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
    <%
        String success = (String) session.getAttribute("success");
        if (success != null) {
    %>
    <div style="color: green; margin-top: 5px;">
        <%= success %>
    </div>
    <%
            session.removeAttribute("success"); // xóa sau khi hiển thị
        }
    %>
    <form action="login" method="post">
        <div class="input-group">
            <label for="usename">Username</label>
            <input type="text" id="usename" name="username" placeholder="username" required>
        </div>

        <div class="input-group">
            <label for="password">Mật khẩu</label>
            <div class="password-container">
                <input type="password" id="password" name="password" placeholder="••••••••" required>
                <span class="toggle-password" onclick="togglePasswordVisibility('password', this)">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>
                </span>
            </div>
        </div>

        <button type="submit" class="submit-btn">Đăng nhập</button>
    </form>
    <%
        String error = (String) session.getAttribute("error");
        if (error != null) {
    %>
    <div style="color: red; margin-top: 10px;">
        <%= error %>
    </div>
    <%
            session.removeAttribute("error"); // xóa sau khi hiển thị
        }
    %>
    <div class="register-link">
        Chưa có tài khoản? <a href="register">Đăng ký ngay</a>
    </div>

    <div class="demo-text">
        Demo username:hello
        pass hello123
    </div>
</div>

<script>
    function togglePasswordVisibility(inputId, iconElement) {
        const passwordInput = document.getElementById(inputId);
        const iconSvg = iconElement.querySelector('svg');
        
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            iconSvg.innerHTML = '<path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path><line x1="1" y1="1" x2="23" y2="23"></line>';
        } else {
            passwordInput.type = 'password';
            iconSvg.innerHTML = '<path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle>';
        }
    }
</script>
</body>

</html>