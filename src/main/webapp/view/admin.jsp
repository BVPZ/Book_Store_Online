<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
    <style>
        body {
            background: #f5f7fb;
            overflow: auto;

        }

        .topbar {
            background: white;
            padding: 15px 30px;
            border-bottom: 1px solid #eee;
        }

        .nav-tabs-custom {
            background: #eee;
            padding: 10px;
            border-radius: 30px;
            display: flex;
            justify-content: space-around;
        }

        .nav-item-custom {
            padding: 8px 20px;
            border-radius: 20px;
            cursor: pointer;
        }

        .nav-item-custom.active {
            background: white;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .card-box {
            background: white;
            border-radius: 15px;
            padding: 20px;
        }
        .chart-big {
            height: 350px;
        }

        .chart-small {
            height: 180px;
        }
    </style>
</head>
<body>

<!-- HEADER -->
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
                        <a href="adduser" class="btn btn-sm btn-primary">
                            Thêm người dùng
                        </a>
                    </th>
                </tr>

                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>${u.userId}</td>
                        <td>${u.username}</td>
                        <td>${u.fullName}</td>
                        <td>${u.email}</td>
                        <td>
                            <a href="edit-user?id=${u.userId}" class="btn btn-sm btn-primary">
                            Chỉnh sửa
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
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
<script>
    function showTab(tab) {
        document.querySelectorAll(".tab-content").forEach(el => el.style.display = "none");
        document.getElementById(tab).style.display = "block";

        document.querySelectorAll(".nav-item-custom").forEach(el => el.classList.remove("active"));
        event.target.classList.add("active");
    }
    const ctx1 = document.getElementById('revenueChart');

    new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: ['T1', 'T2', 'T3', 'T4'],
            datasets: [{
                label: 'Doanh thu',
                data: [12000000, 15000000, 18000000, 10000000]
            }]
        },
        options: {
            maintainAspectRatio: false
        }
    });
    const ctx2 = document.getElementById('orderChart');

    new Chart(ctx2, {
        type: 'line',
        data: {
            labels: ['T1', 'T2', 'T3', 'T4'],
            datasets: [{
                label: 'Đơn hàng',
                data: [150, 180, 240, 130]
            }]
        },
        options: {
            maintainAspectRatio: false
        }
    });

    const ctx = document.getElementById('categoryChart');

    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Văn học', 'Kinh doanh', 'Khoa học', 'Giả tưởng', 'Khác'],
            datasets: [{
                data: [35, 25, 20, 15, 5],
                backgroundColor: [
                    '#10b981',
                    '#3b82f6',
                    '#f59e0b',
                    '#8b5cf6',
                    '#9ca3af'
                ]
            }]
        },
        options: {
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false // ẩn legend nếu bạn muốn giống hình
                },
                datalabels: {
                    color: function(context) {
                        return context.dataset.backgroundColor[context.dataIndex];
                    },
                    formatter: function(value, context) {
                        let label = context.chart.data.labels[context.dataIndex];
                        return label + ': ' + value + '%';
                    },
                    font: {
                        weight: 'bold',
                        size: 14
                    },
                    anchor: 'end',
                    align: 'end',
                    offset: 10
                }
            }
        },
        plugins: [ChartDataLabels]
    });
</script>

</body>
</html>