<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.bookshop.model.User" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đơn hàng của tôi - Nhà Sách Tri Thức</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
    <style>
        body { background-color: #f8f9fa; }
        /* Header styles từ bản mẫu */
        .logo-text { font-weight: bold; color: #198754; font-size: 1.2rem; line-height: 1; }
        .logo-sub { font-size: 11px; color: #6c757d; }
        .search-btn { background-color: #00a651; color: white; border: none; }
        .search-btn:hover { background-color: #008541; color: white; }
        .icon-btn { text-decoration: none; color: #333; display: flex; flex-direction: column; align-items: center; font-size: 11px; margin-left: 20px; }
        .icon-btn i { font-size: 1.5rem; margin-bottom: 2px; }
        .nav-link-custom { text-decoration: none; color: #333; font-size: 14px; }
        .nav-promo { font-size: 13px; color: #198754; }
        .nav-promo.orange { color: #fd7e14; }

        /* Order styles hiện tại của bạn */
        .sidebar-filter { background: white; border-radius: 12px; padding: 20px; }
        .filter-item { display: flex; justify-content: space-between; padding: 12px 10px; border-radius: 8px; cursor: pointer; color: #6c757d; text-decoration: none; transition: all 0.2s; }
        .filter-item:hover { background-color: #f1f3f5; }
        .filter-item.active { background-color: #e8f5e9; color: #2e7d32; font-weight: bold; }
        .order-card { background: white; border-radius: 12px; border: none; margin-bottom: 20px; transition: 0.3s; }
        .order-card:hover { box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        .status-badge { border-radius: 20px; padding: 5px 15px; font-size: 0.85rem; font-weight: 600; }
        .status-Completed { background: #e8f5e9; color: #2e7d32; }
        .status-Shipping { background: #e3f2fd; color: #1976d2; }
        .status-Processing, .status-Pending { background: #fff8e1; color: #fbc02d; }
        .status-Cancelled { background: #ffebee; color: #c62828; }

        /* 1. Mở khóa thanh cuộn dọc */
        html, body {
            height: auto !important;
            overflow-y: auto !important;
            overflow-x: hidden !important;
        }

        /* 2. Giúp thanh lọc bên trái đứng yên khi cuộn trang (Sticky) */
        .sidebar-filter {
            position: sticky;
            top: 20px;
            z-index: 100;
        }

        /* 3. Đảm bảo container chính có đủ không gian */
        .container.py-5 {
            min-height: 100vh;
            display: block !important;
        }

    </style>
</head>
<body>

<header class="bg-white border-bottom py-3">
    <div class="container-fluid px-4">
        <div class="row align-items-center">
            <div class="col-md-3 d-flex align-items-center">
                <a href="${pageContext.request.contextPath}/home" class="d-flex align-items-center text-decoration-none text-dark">
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
                        <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm sách..." style="border-color: #00a651; box-shadow: none;">
                        <button class="btn search-btn px-4" type="submit"><i class="bi bi-search"></i> Tìm kiếm</button>
                    </div>
                </form>
            </div>
            <div class="col-md-3 d-flex justify-content-end">
                <a href="#" class="icon-btn"><i class="bi bi-box-seam"></i>Tra cứu đơn</a>
                <a href="cart" class="icon-btn"><i class="bi bi-cart2"></i>Giỏ hàng</a>
                <%
                    User user = (User) session.getAttribute("user");
                    if (user == null) {
                %>
                <a href="login" class="icon-btn"><i class="bi bi-person"></i>Đăng nhập</a>
                <% } else { %>
                <a href="profile" class="icon-btn"><i class="bi bi-person"></i>Tài khoản</a>
                <% } %>
            </div>
        </div>
    </div>
</header>

<nav class="bg-white border-bottom py-2 shadow-sm mb-4">
    <div class="container-fluid px-4 d-flex justify-content-between align-items-center">
        <div>
            <a href="#" class="nav-link-custom fw-bold"><i class="bi bi-list me-2"></i> DANH MỤC SÁCH</a>
            <a href="${pageContext.request.contextPath}/recently-viewed" class="nav-link-custom border-start ms-2 ps-3">Sản phẩm đã xem</a>
        </div>
        <div class="d-flex gap-4">
            <span class="nav-promo"><i class="bi bi-truck me-1"></i> Ship COD Toàn Quốc</span>
            <span class="nav-promo orange"><i class="bi bi-gift me-1"></i> Free Ship Đơn > 300k</span>
        </div>
    </div>
</nav>

<div class="container py-1">
    <div class="d-flex align-items-center mb-4">
        <div class="bg-success text-white rounded-3 p-3 me-3">
            <i class="fas fa-box-open fa-2x"></i>
        </div>
        <div>
            <h2 class="fw-bold mb-0">Đơn hàng của tôi</h2>

        </div>
    </div>

    <div class="mb-4">
        <div class="input-group">
            <span class="input-group-text bg-white border-end-0"><i class="fas fa-search text-muted"></i></span>
            <input type="text" id="searchInput" class="form-control border-start-0" placeholder="Tìm kiếm theo mã đơn hàng..." onkeyup="searchOrders()">
        </div>
    </div>

    <div class="row">
        <div class="col-lg-3">
            <div class="sidebar-filter shadow-sm">
                <h6 class="fw-bold mb-3"><i class="fas fa-filter me-2"></i>Lọc theo trạng thái</h6>
                <a href="#" class="filter-item active" onclick="filterOrders(event, 'All')">
                    <span>Tất cả</span>
                </a>
                <a href="#" class="filter-item" onclick="filterOrders(event, 'Pending')">
                    <span><i class="fas fa-clock me-2"></i>Chờ xác nhận</span>
                </a>
                <a href="#" class="filter-item" onclick="filterOrders(event, 'Processing')">
                    <span><i class="fas fa-box me-2"></i>Đang xử lý</span>
                </a>
                <a href="#" class="filter-item" onclick="filterOrders(event, 'Shipping')">
                    <span><i class="fas fa-truck me-2"></i>Đang giao</span>
                </a>
                <a href="#" class="filter-item" onclick="filterOrders(event, 'Completed')">
                    <span><i class="fas fa-check-circle me-2"></i>Đã giao</span>
                </a>
                <a href="#" class="filter-item" onclick="filterOrders(event, 'Cancelled')">
                    <span><i class="fas fa-times-circle me-2"></i>Đã hủy</span>
                </a>
            </div>
        </div>

        <div class="col-lg-9">
            <c:choose>
                <c:when test="${not empty orderList}">
                    <div id="order-container">
                   <c:forEach var="order" items="${orderList}">
                       <div class="card order-card shadow-sm p-3 order-item" data-status="${order.orderStatus}" data-id="${order.orderId}">
                           <div class="d-flex justify-content-between align-items-start">
                               <div>
                                   <h6 class="fw-bold mb-1 order-id-text">ORD-${order.orderId}
                                       <span class="status-badge ms-2 status-${order.orderStatus}">
                                           <c:if test="${order.orderStatus == 'Completed'}"><i class="fas fa-check me-1"></i>Đã giao</c:if>
                                           <c:if test="${order.orderStatus == 'Shipping'}"><i class="fas fa-truck me-1"></i>Đang giao</c:if>
                                           <c:if test="${order.orderStatus == 'Processing'}"><i class="fas fa-box me-1"></i>Đang xử lý</c:if>
                                           <c:if test="${order.orderStatus == 'Pending'}"><i class="fas fa-clock me-1"></i>Chờ xác nhận</c:if>
                                           <c:if test="${order.orderStatus == 'Cancelled'}"><i class="fas fa-times me-1"></i>Đã hủy</c:if>
                                       </span>
                                   </h6>
                                   <p class="text-muted small mb-3">Ngày đặt: ${order.orderDate}</p>
                                   <div class="d-flex text-muted small">
                                       <span class="me-4"><i class="fas fa-shopping-bag me-1"></i>Sản phẩm</span>
                                   </div>
                               </div>
                               <div class="text-end">
                                   <p class="text-muted small mb-1">Tổng tiền</p>
                                   <h5 class="text-success fw-bold">
                                       <fmt:setLocale value="vi_VN"/>
                                       <fmt:formatNumber value="${order.totalPrice}" type="currency"/>
                                   </h5>

                                   <button class="btn btn-outline-success btn-sm mt-2 detail-btn"
                                           type="button"
                                           data-bs-toggle="collapse"
                                           data-bs-target="#collapseOrder${order.orderId}"
                                           aria-expanded="false">
                                       <i class="far fa-eye me-1"></i> <span class="btn-text">Xem chi tiết</span>
                                   </button>
                               </div>
                           </div>

                           <div class="collapse mt-4 pt-3 border-top" id="collapseOrder${order.orderId}">
                               <h6 class="fw-bold mb-3 text-dark">Sản phẩm đã đặt</h6>

                               <c:forEach var="detail" items="${order.orderDetails}">
                                   <div class="d-flex align-items-center p-3 bg-white rounded-3 mb-3 border shadow-sm">

                                       <div class="me-3">
                                           <img src="${detail.book.imageUrl}"
                                                alt="${detail.book.title}"
                                                style="width: 70px; height: 95px; object-fit: cover;"
                                                class="rounded-2 border shadow-sm">
                                       </div>

                                       <div class="flex-grow-1">
                                           <h6 class="mb-1 fw-bold text-dark" style="font-size: 1.05rem;">${detail.book.title}</h6>
                                           <p class="text-muted small mb-0">
                                               <i class="fas fa-user-edit me-1"></i>${detail.book.authorName}
                                           </p>
                                       </div>

                                       <div class="text-end">
                                           <div class="text-muted small mb-1">x${detail.quantity}</div>
                                           <div class="fw-bold text-success" style="font-size: 1.1rem;">
                                               <fmt:setLocale value="vi_VN"/>
                                               <%-- QUAN TRỌNG: Thêm integerOnly="true" để loại bỏ phần .00 gây hiểu lầm --%>
                                               <fmt:parseNumber var="priceNum" value="${detail.price}" type="number" integerOnly="true" />
                                               <fmt:formatNumber value="${priceNum}" type="currency"/>
                                           </div>
                                       </div>
                                   </div>
                               </c:forEach>

                               <h6 class="fw-bold mb-2 mt-4">Địa chỉ nhận hàng</h6>
                               <div class="bg-light p-3 rounded mb-4 text-muted border">
                                   123 Đường ABC, Quận 1, TP.HCM
                               </div>

                               <div class="d-flex gap-2">
                                   <c:choose>
                                       <%-- 1. Trạng thái: ĐÃ GIAO (Completed) --%>
                                       <c:when test="${order.orderStatus == 'Completed'}">
                                           <button class="btn btn-success btn-sm px-3">
                                               <i class="fas fa-star me-1"></i> Đánh giá sản phẩm
                                           </button>
                                           <button class="btn btn-outline-secondary btn-sm px-3">Mua lại</button>
                                       </c:when>

                                       <%-- 2. Trạng thái: ĐÃ HỦY (Cancelled) --%>
                                       <c:when test="${order.orderStatus == 'Cancelled'}">
                                           <button class="btn btn-outline-secondary btn-sm px-3">Mua lại</button>
                                       </c:when>

                                       <%-- 3. Trạng thái: ĐANG XỬ LÝ (Processing) --%>
                                       <c:when test="${order.orderStatus == 'Processing'}">
                                           <span class="text-muted small italic">Đang chuẩn bị hàng...</span>
                                       </c:when>

                                       <%-- 4. Các trạng thái khác (Chờ xác nhận, Đang giao) --%>
                                       <c:otherwise>
                                           </c:otherwise>
                                   </c:choose>
                               </div>
                           </div>
                       </div>
                   </c:forEach>
                    </div>

                    <div id="empty-state" class="text-center py-5" style="display: none;">
                        <img src="https://cdn-icons-png.flaticon.com/512/4076/4076432.png" width="80" class="mb-3 opacity-50">
                        <p class="text-muted">Không có đơn hàng nào ở trạng thái này.</p>
                    </div>

                </c:when>
                <c:otherwise>
                    <div class="text-center py-5 bg-white rounded-3 shadow-sm">
                        <img src="https://cdn-icons-png.flaticon.com/512/4076/4076432.png" width="100" class="mb-3 opacity-50">
                        <p class="text-muted">Bạn chưa có đơn hàng nào.</p>
                        <a href="home" class="btn btn-success px-4">Tiếp tục mua sắm</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script>
    // Hàm Lọc đơn hàng theo trạng thái
    function filterOrders(event, status) {
        event.preventDefault(); // Chặn hành vi nhảy lên đầu trang của thẻ <a>

        // 1. Đổi màu nút được click
        let filters = document.querySelectorAll('.filter-item');
        filters.forEach(item => item.classList.remove('active'));
        event.currentTarget.classList.add('active');

        // 2. Lọc các thẻ đơn hàng
        let orders = document.querySelectorAll('.order-item');
        let visibleCount = 0;

        orders.forEach(order => {
            // Lấy trạng thái của thẻ đó (ví dụ: "Completed", "Pending")
            let orderStatus = order.getAttribute('data-status');

            if (status === 'All' || orderStatus === status) {
                order.style.display = 'block';
                visibleCount++;
            } else {
                order.style.display = 'none';
            }
        });

        // 3. Hiển thị thông báo nếu không có đơn hàng nào khớp
        let emptyState = document.getElementById('empty-state');
        if(emptyState) {
            emptyState.style.display = (visibleCount === 0) ? 'block' : 'none';
        }
    }

    // Hàm Tìm kiếm đơn hàng theo Mã (Bonus thêm cho bạn)
    function searchOrders() {
        let input = document.getElementById('searchInput').value.toLowerCase();
        let orders = document.querySelectorAll('.order-item');
        let visibleCount = 0;

        // Reset lại bộ lọc trạng thái về "Tất cả" khi tìm kiếm
        document.querySelectorAll('.filter-item').forEach(item => item.classList.remove('active'));
        document.querySelector('.filter-item').classList.add('active');

        orders.forEach(order => {
            let orderId = "ord-" + order.getAttribute('data-id');
            if (orderId.includes(input)) {
                order.style.display = 'block';
                visibleCount++;
            } else {
                order.style.display = 'none';
            }
        });

        let emptyState = document.getElementById('empty-state');
        if(emptyState) {
            emptyState.style.display = (visibleCount === 0) ? 'block' : 'none';
        }
    }
</script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Lấy tất cả các phần tử có class collapse
        var collapseElements = document.querySelectorAll('.collapse');

        collapseElements.forEach(function (collapseEl) {
            // Khi thẻ bắt đầu trượt xuống (Mở)
            collapseEl.addEventListener('show.bs.collapse', function (e) {
                var targetId = e.target.id;
                var btn = document.querySelector('[data-bs-target="#' + targetId + '"]');
                if(btn) {
                    btn.innerHTML = '<i class="far fa-eye-slash me-1"></i> <span class="btn-text">Thu gọn</span>';
                }
            });

            // Khi thẻ bắt đầu trượt lên (Đóng)
            collapseEl.addEventListener('hide.bs.collapse', function (e) {
                var targetId = e.target.id;
                var btn = document.querySelector('[data-bs-target="#' + targetId + '"]');
                if(btn) {
                    btn.innerHTML = '<i class="far fa-eye me-1"></i> <span class="btn-text">Xem chi tiết</span>';
                }
            });
        });
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>