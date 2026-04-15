<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_style.css">
</head>
<body>

<div class="topbar d-flex justify-content-between">
    <div class="col-md-3 d-flex align-items-center">
        <div class="bg-success text-white rounded p-2 me-2">
            <i class="bi bi-book"></i>
    </div>
        <div>
            <div class="logo-text">Nhà Sách Tri Thức</div>
            <div class="logo-sub">Đọc sách - Mở tương lai</div>
        </div>
    </div>
    <h4>Admin Dashboard</h4>
    <div>
        Xin chào, Admin
        <a href="logout" class="btn btn-danger btn-sm">Đăng xuất</a>
    </div>

</div>

<div class="container mt-4">

    <!-- TAB -->
    <div class="nav-tabs-custom mb-4">
        <div class="nav-item-custom active" onclick="showTab('dashboard')">Dashboard</div>
        <div class="nav-item-custom" onclick="showTab('users')">Người dùng</div>
        <div class="nav-item-custom" onclick="showTab('books')">Sách</div>
        <div class="nav-item-custom" onclick="showTab('orders')">Đơn hàng</div>
    </div>

    <!-- DASHBOARD -->
    <div id="dashboard" class="tab-content">
        <div class="row">
            <div class="col-md-3">
                <div class="card-box">
                    <h6>Doanh thu</h6>
                    <h3 class="text-success">202M</h3>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-box">
                    <h6>Đơn hàng</h6>
                    <h3 class="text-primary">704</h3>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-box">
                    <h6>Người dùng</h6>
                    <h3 class="text-purple">12</h3>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-box">
                    <h6>Sản phẩm</h6>
                    <h3 class="text-warning">60</h3>
                </div>
            </div>
        </div>
        <div class="row mt-4">
            <div class="col-md-6" >

                <canvas id="revenueChart" height="300"></canvas>
            </div>

            <div class="col-md-6">
                <canvas id="categoryChart" height="80"></canvas>
            </div>
        </div>

        <div class="mt-4">
            <canvas id="orderChart" width="400"></canvas>
        </div>
    </div>

    <!-- USERS -->
    <div id="users" class="tab-content" style="display:none">
        <div class="card-box">
            <h5>Quản lý người dùng</h5>
            <table class="table">
                <tr>
                    <th>ID</th><th>UserName</th><th>FullName</th><th>Email</th>
                    <th>
                        <button class="btn-add" onclick="openAddModal()">Thêm người dùng</button>
                    </th>
                    <th>Xóa người dùng</th>
                </tr>

                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>${u.userId}</td>
                        <td>${u.username}</td>
                        <td>${u.fullName}</td>
                        <td>${u.email}</td>
                        <td>
                            <button type="button" class="btn btn-sm btn-primary"
                                    onclick="openEditModal('${u.userId}', '${u.username}', '${u.fullName}', '${u.email}', '${u.address}' , '${u.phone}')">
                                Chỉnh sửa
                            </button>
                        </td>
                        <td>
                            <a href="delete-user?id=${u.userId}">
                                <button type="button" class="btn-delete" style="border: solid ;background-color: red">
                                    Xóa
                                </button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </div>

    <!-- BOOKS -->
    <div id="books" class="tab-content" style="display:none">
        <div class="card-box">
            <h5>Quản lý sách</h5>
            <c:forEach var="b" items="${books}">
                <div class="d-flex align-items-center border-bottom py-2 justify-content-between">
                    <!-- Bên trái -->
                    <div class="d-flex align-items-center">
                        <img src="${b.imageUrl}" width="50">
                        <div class="ms-3">
                            <b>${b.title}</b><br>
                                ${b.price}
                        </div>
                    </div>
                    <!-- Nút bên phải -->
                    <div>
                        <a href="edit-book?id=${b.bookId}" class="btn btn-sm btn-primary">
                            Chỉnh sửa
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- ORDERS -->
    <div id="orders" class="tab-content" style="display:none">
        <div class="card-box">
            <h5>Quản lý đơn hàng</h5>
            <table class="table">
                <tr>
                    <th>Mã</th><th>Khách</th><th>Tổng</th>
                </tr>
                <c:forEach var="o" items="${orders}">
                    <tr>
                        <td>${o.id}</td>
                        <td>${o.customerName}</td>
                        <td>${o.total}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

</div>
<div id="userModal" class="modal" style="display: none;">
    <div class="modal-content">
        <span class="close-btn" onclick="closeModal()">&times;</span>
        <h2 id="modalTitle">Thông tin người dùng</h2>
        <form action="edit-user" method="post">
            <input type="hidden" id="action" name="action" value="add">
            <input type="hidden" id="userId" name="id">
            <div id="authFields" style="display: none;">
                <div class="form-group">
                    <label>Username:</label>
                    <input type="text" id="userName" name="userName">
                </div>
                <div class="form-group">
                    <label>Password:</label>
                    <input type="password" id="password" name="password">
                </div>
            </div>
            <div class="form-group">
                <label>FullName:</label>
                <input type="text" id="fullName" name="fullName">
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" id="email" name="email">
            </div>
            <div class="form-group">
                <label>Phone number:</label>
                <input type="text" id="phoneNumber" name="phoneNumber">
            </div>
            <div class="form-group">
                <label>Address:</label>
                <input type="text" id="address" name="address">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn-save">Xác nhận</button>
                <button type="button" onclick="closeModal()">Hủy</button>
            </div>
        </form>
    </div>
</div>
<c:if test="${not empty status}">
    <script>alert('${status}')</script>
    <c:remove var="status" scope="session"/>
</c:if>
<c:if test="${not empty showTab}">
    <script>
        window.onload = function() {
            const tab = "${showTab}";
            const tabBtn = document.querySelector(`[onclick*="${showTab}"]`);
            if(tabBtn){
                showTab(tab, { target: tabBtn });
            }
        }
    </script>
    <c:remove var="showTab" scope="session"/>
</c:if>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
<script src="${pageContext.request.contextPath}/js/admin.js"></script>

</body>
</html>