<%@ page import="com.bookshop.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nhà Sách Tri Thức - Tài Khoản</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
</head>
<body>

<header class="top-header">
    <div class="container-fluid px-4">
        <div class="row align-items-center">
            <div class="col-md-3 d-flex align-items-center">
                <a href="${pageContext.request.contextPath}/home"
                   class="d-flex align-items-center text-decoration-none text-dark">
                    <div class="bg-success text-white rounded p-2 me-2">
                        <i class="bi bi-book"></i>
                    </div>
                    <div>
                        <div class="logo-text">Nhà Sách Tri Thức</div>
                        <div class="logo-sub">Đọc sách - Mở tương lai</div>
                    </div>
                </a>
            </div>
            <div class="col-md-6">
                <form action="search" method="GET">
                    <div class="input-group">
                        <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm sách, tác giả..." style="border-color: #00a651; box-shadow: none;">
                        <button class="btn search-btn" type="submit"><i class="bi bi-search"></i> Tìm kiếm</button>
                    </div>
                </form>
            </div>
            <div class="col-md-3 d-flex justify-content-end">
                <a href="#" class="icon-btn"><i class="bi bi-box-seam"></i>Tra cứu đơn hàng</a>
                <a href="cart" class="icon-btn"><i class="bi bi-cart2"></i>Giỏ hàng</a>

                <%
                    user = (User) session.getAttribute("user");
                    if (user == null) {
                %>
                <a href="login" class="icon-btn">
                    <i class="bi bi-person"></i>Đăng nhập
                </a>
                <%
                } else {
                %>
                <a href="profile" class="icon-btn">
                    <i class="bi bi-person"></i>Tài khoản
                </a>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</header>

<nav class="main-nav">
    <div class="container-fluid px-4">
        <div class="d-flex justify-content-between align-items-center">
            <div>
                <a href="#" class="nav-link-custom fw-bold"><i class="bi bi-list me-2"></i> DANH MỤC SÁCH</a>
                <a href="#" class="nav-link-custom border-start ms-2 ps-3">Sản phẩm đã xem</a>
            </div>
            <div class="d-flex gap-4">
                <span class="nav-promo"><i class="bi bi-truck me-1"></i> Ship COD Trên Toàn Quốc</span>
                <span class="nav-promo orange"><i class="bi bi-gift me-1"></i> Free Ship Đơn Hàng Trên 300k</span>
            </div>
        </div>
    </div>
</nav>
<main class="container main-layout-container">
    <aside class="sidebar-info">
        <div class="sidebar-user-header">
            <div class="user-avatar-circle">
                <i class="bi bi-person"></i>
            </div>
            <div class="fw-bold mt-2">
                <%= (user != null && user.getFullName() != null) ? user.getFullName() : "Khách hàng" %>
            </div>
            <div class="small opacity-75">
                <%= (user != null && user.getEmail() != null) ? user.getEmail() : "" %>
            </div>
        </div>

        <div class="sidebar-nav-menu">
            <a href="#" class="nav-menu-item active" id="nav-profile-info">Thông tin cá nhân</a>
            <a href="#" class="nav-menu-item">Đơn hàng của tôi</a>
            <a href="#" class="nav-menu-item" id="nav-change-password">Đổi mật khẩu</a>
            <hr>
            <a href="logout" class="nav-menu-item text-danger">Đăng xuất</a>
        </div>
    </aside>

    <div class="main-profile-content">
        <div class="info-details-card" id="profile-card">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h5 class="fw-bold m-0">Thông tin cá nhân</h5>
                <button class="btn-edit-profile" id="btn-edit">Chỉnh sửa</button>
            </div>

            <div id="view-mode">
                <div class="row g-4">
                    <div class="col-md-6">
                        <label class="info-label">Họ và tên</label>
                        <div class="info-display-box">
                            <%= (user != null && user.getFullName() != null) ? user.getFullName() : "Chưa cập nhật" %>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <label class="info-label">Email</label>
                        <div class="info-display-box">
                            <%= (user != null && user.getEmail() != null) ? user.getEmail() : "Chưa cập nhật" %>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <label class="info-label">Số điện thoại</label>
                        <div class="info-display-box">
                            <%= (user != null && user.getPhone() != null) ? user.getPhone() : "Chưa cập nhật" %>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <label class="info-label">Địa chỉ</label>
                        <div class="info-display-box">
                            <%= (user != null && user.getAddress() != null) ? user.getAddress() : "Chưa cập nhật" %>
                        </div>
                    </div>
                </div>
            </div>

            <form id="edit-mode" action="updateProfile" method="POST" style="display: none;">
                <div class="row g-4">
                    <div class="col-md-6">
                        <label class="info-label">Họ và tên</label>
                        <input type="text" name="fullName" class="form-control" value="<%= (user != null && user.getFullName() != null) ? user.getFullName() : "" %>">
                    </div>
                    <div class="col-md-6">
                        <label class="info-label">Email</label>
                        <input type="email" name="email" class="form-control" value="<%= (user != null && user.getEmail() != null) ? user.getEmail() : "" %>">
                    </div>
                    <div class="col-md-6">
                        <label class="info-label">Số điện thoại</label>
                        <input type="text" name="phone" class="form-control" value="<%= (user != null && user.getPhone() != null) ? user.getPhone() : "" %>">
                    </div>
                    <div class="col-md-6">
                        <label class="info-label">Địa chỉ</label>
                        <input type="text" name="address" class="form-control" value="<%= (user != null && user.getAddress() != null) ? user.getAddress() : "" %>">
                    </div>
                </div>
                <div class="d-flex justify-content-end mt-4">
                    <button type="button" class="btn btn-secondary me-2" id="btn-cancel-edit">Huỷ</button>
                    <button type="submit" class="btn btn-success">Lưu thay đổi</button>
                </div>
            </form>
        </div>

        <div class="info-details-card" id="change-password-card" style="display: none;">
            <div class="mb-4">
                <h5 class="fw-bold m-0">Đổi mật khẩu</h5>
            </div>
            <form action="changePassword" method="POST">
                <div class="mb-3">
                    <label class="info-label">Mật khẩu hiện tại</label>
                    <input type="password" name="oldPassword" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="info-label">Mật khẩu mới</label>
                    <input type="password" name="newPassword" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="info-label">Nhập lại mật khẩu mới</label>
                    <input type="password" name="confirmPassword" class="form-control" required>
                </div>
                <div class="d-flex mt-4">
                    <button type="submit" class="btn btn-success">Lưu thay đổi</button>
                </div>
            </form>
        </div>

        <div id="stats-section">
            <h5 class="mt-4 fw-bold mb-3">Thống kê</h5>
            <div class="row g-3">
                <div class="col-md-6">
                    <div class="stat-card bg-green">
                        <div class="stat-value"><%= request.getAttribute("orderCount") != null ? request.getAttribute("orderCount") : "0" %></div>
                        <div>Đơn hàng</div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="stat-card bg-blue">
                        <div class="stat-value"><%= request.getAttribute("booksPurchased") != null ? request.getAttribute("booksPurchased") : "0" %></div>
                        <div>Sách đã mua</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const btnEdit = document.getElementById("btn-edit");
        const btnCancelEdit = document.getElementById("btn-cancel-edit");
        const viewMode = document.getElementById("view-mode");
        const editMode = document.getElementById("edit-mode");

        const navProfileInfo = document.getElementById("nav-profile-info");
        const navChangePassword = document.getElementById("nav-change-password");

        const profileCard = document.getElementById("profile-card");
        const statsSection = document.getElementById("stats-section");
        const changePasswordCard = document.getElementById("change-password-card");

        if (btnEdit) {
            btnEdit.addEventListener("click", function() {
                viewMode.style.display = "none";
                editMode.style.display = "block";
                btnEdit.style.display = "none";
            });
        }

        if (btnCancelEdit) {
            btnCancelEdit.addEventListener("click", function() {
                editMode.style.display = "none";
                viewMode.style.display = "block";
                btnEdit.style.display = "block";
            });
        }

        if (navProfileInfo && navChangePassword) {
            navProfileInfo.addEventListener("click", function(e) {
                e.preventDefault();
                navProfileInfo.classList.add("active");
                navChangePassword.classList.remove("active");

                profileCard.style.display = "block";
                if (statsSection) statsSection.style.display = "block";
                changePasswordCard.style.display = "none";
            });

            navChangePassword.addEventListener("click", function(e) {
                e.preventDefault();
                navChangePassword.classList.add("active");
                navProfileInfo.classList.remove("active");

                profileCard.style.display = "none";
                if (statsSection) statsSection.style.display = "none";
                changePasswordCard.style.display = "block";
            });
        }
    });
</script>
</body>
</html>