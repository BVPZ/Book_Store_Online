<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết đơn hàng</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order_detail.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_style.css">
</head>
<body>

<!-- ===== HEADER ===== -->
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

<!-- ===== CONTENT ===== -->
<div class="container mt-4">

    <!--  Thông tin đơn hàng -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            Thông tin đơn hàng
        </div>
        <div class="card-body">
            <p><b>Mã đơn:</b> ${order.orderId}</p>
            <p><b>Ngày đặt:</b> ${order.orderDate}</p>
            <p><b>Giao hàng:</b> ${order.informationDelivery}</p>
            <p><b>Tổng tiền:</b> ${order.totalPrice} VND</p>
        </div>
    </div>

    <!--  Thanh toán -->
    <div class="card mb-4">
        <div class="card-header bg-success text-white">
            Thanh toán
        </div>
        <div class="card-body">
            <p><b>Phương thức:</b> ${payment.paymentMethod}</p>
        </div>
    </div>

    <!--  Danh sách sản phẩm -->
    <div class="card">
        <div class="card-header bg-dark text-white">
            Chi tiết sản phẩm
        </div>
        <div class="card-body">

            <table class="table table-bordered align-middle">
                <thead class="table-light">
                <tr>
                    <th>Ảnh</th>
                    <th>Tên sách</th>
                    <th>Số lượng</th>
                    <th>Giá bán</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="od" items="${orderDetails}">
                    <tr>
                        <td style="width:100px">
                            <img src="${od.book.imageUrl}"
                                 style="width:80px; height:100px; object-fit:cover;">
                        </td>
                        <td>${od.book.title}</td>
                        <td>${od.quantity}</td>
                        <td class = "price">${od.price} VND</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>

</div>

</body>
</html>