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
                                              <%--
                                                  Sửa tại đây: Không dùng parseNumber vì nó hiểu nhầm dấu chấm thập phân là dấu phân cách hàng nghìn.
                                                  Dùng trực tiếp formatNumber với maxFractionDigits="0" để bỏ phần .00
                                              --%>
                                              <fmt:formatNumber value="${detail.price}" type="currency" maxFractionDigits="0"/>
                                          </div>
                                      </div>
                                      <div class="ms-3">
                                          <a href="book-detail?id=${detail.book.bookId}"
                                             class="btn btn-outline-success btn-sm d-flex align-items-center gap-1 px-3 py-1">
                                              <i class="fas fa-rotate-right"></i>
                                              Mua lại
                                          </a>
                                      </div>
                                   </div>
                               </c:forEach>

        <h6 class="fw-bold mb-2 mt-4">Thông tin giao hàng</h6>
        <div class="bg-light p-3 rounded mb-4 text-muted border">
            <div style="line-height: 1.8;">
                <%
                    // Lấy chuỗi từ object order
                    String info = (String) pageContext.findAttribute("order").getClass().getMethod("getInformationDelivery").invoke(pageContext.findAttribute("order"));
                    if (info != null) {
                        // Tách các mục dựa trên khoảng trắng lớn hoặc dấu hiệu nhận biết tiêu đề
                        // Ở đây mình tách theo tên các trường bạn có
                        String[] fields = {"Họ và tên:", "Số điện thoại:", "Email:", "Địa chỉ:", "Quận/Huyện:", "Tỉnh/Thành phố:", "Ghi chú:"};

                        for (int i = 0; i < fields.length; i++) {
                            int start = info.indexOf(fields[i]);
                            if (start != -1) {
                                int end = (i < fields.length - 1) ? info.indexOf(fields[i+1]) : info.length();
                                if (end == -1) end = info.length();

                                String content = info.substring(start + fields[i].length(), end).trim();

                                // CHỈ IN RA NẾU CÓ DỮ LIỆU
                                if (!content.isEmpty() && !content.equals("null") && !content.equals("không")) {
                                    if (fields[i].equals("Quận/Huyện:") || fields[i].equals("Tỉnh/Thành phố:")) {
                                        out.print(", " + fields[i] + " " + content);
                                    } else {
                                        if (!fields[i].equals("Họ và tên:")) out.print("<br/>");
                                        out.print("<strong>" + fields[i] + "</strong> " + content);
                                    }
                                }
                            }
                        }
                    }
                %>
            </div>
        </div>
                             <div class="d-flex gap-2">
                                 <c:choose>
                                 <%-- TRƯỜNG HỢP 1: CHỜ XÁC NHẬN (Pending) --%>
                                 <c:when test="${order.orderStatus == 'Pending'}">
                                     <form action="update-orderStatus" method="POST" onsubmit="return confirm('Bạn có muốn hủy đơn hàng này không?');" class="d-inline">
                                         <%-- Truyền ngầm các dữ liệu mà Servlet cần --%>
                                         <input type="hidden" name="orderId" value="${order.orderId}">
                                         <input type="hidden" name="status" value="Cancelled">
                                         <input type="hidden" name="action" value="userAction">

                                         <button type="submit" class="btn btn-danger btn-sm px-3">
                                             <i class="fas fa-trash-alt me-1"></i> Hủy đơn hàng
                                         </button>
                                     </form>
                                 </c:when>

                                 <%-- TRƯỜNG HỢP 2: ĐANG XỬ LÝ (Processing) --%>
                                 <c:when test="${order.orderStatus == 'Processing'}">
                                     <form action="update-orderStatus" method="POST" onsubmit="return confirm('Bạn có muốn hủy đơn hàng này không?');" class="d-inline">
                                         <input type="hidden" name="orderId" value="${order.orderId}">
                                         <input type="hidden" name="status" value="Cancelled">
                                         <input type="hidden" name="action" value="userAction">

                                         <button type="submit" class="btn btn-danger btn-sm px-3">
                                             <i class="fas fa-trash-alt me-1"></i> Hủy đơn hàng
                                         </button>
                                     </form>
                                 </c:when>
                                     <%-- TRƯỜNG HỢP 3: ĐANG GIAO (Shipping) --%>
                                     <%-- Thường trạng thái này không cho hủy nữa để tránh shipper đi rồi mới hủy --%>
                                     <c:when test="${order.orderStatus == 'Shipping'}">
                                         <span class="text-primary small fw-bold align-self-center">
                                             <i class="fas fa-shipping-fast me-1"></i> Đang giao hàng
                                         </span>
                                     </c:when>

                                     <%-- TRƯỜNG HỢP DỰ PHÒNG (Nếu có lỗi hoặc trạng thái lạ) --%>
                                     <c:otherwise>
                                         <span class="text-muted small italic">Liên hệ hỗ trợ</span>
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

<jsp:include page="footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>