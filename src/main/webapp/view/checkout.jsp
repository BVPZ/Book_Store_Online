<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.bookshop.model.User" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Nhà Sách Tri Thức - Thanh Toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/checkout.css">
</head>
<body>

<header class="top-header">
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
                        <input type="text" class="form-control" placeholder="Tìm kiếm sách, tác giả..." style="border-color: #00a651; box-shadow: none;">
                        <button class="btn search-btn" type="submit"><i class="bi bi-search"></i> Tìm kiếm</button>
                    </div>
                </form>
            </div>
            <div class="col-md-3 d-flex justify-content-end">
                <a href="#" class="icon-btn"><i class="bi bi-box-seam"></i>Tra cứu đơn hàng</a>
                <a href="cart" class="icon-btn"><i class="bi bi-cart2"></i>Giỏ hàng</a>

                <%
                    User user = (User) session.getAttribute("user");
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
                <a href="${pageContext.request.contextPath}/recently-viewed" class="nav-link-custom border-start ms-2 ps-3">Sản phẩm đã xem</a>
            </div>
            <div class="d-flex gap-4">
                <span class="nav-promo"><i class="bi bi-truck me-1"></i> Ship COD Trên Toàn Quốc</span>
                <span class="nav-promo orange"><i class="bi bi-gift me-1"></i> Free Ship Đơn Hàng Trên 300k</span>
            </div>
        </div>
    </div>
</nav>

<div class="container mt-4 mb-5">
    <div class="row g-4">
        <div class="col-lg-8">
            <form action="order" method="POST" id="checkoutForm">
                <input type="hidden" name="total" value="${total}">
                <section class="section-box mb-4">
                    <h2 class="section-title">Thông tin giao hàng</h2>
                    <div class="input-row">
                        <input type="text" name="txtFullname" placeholder="Họ và tên *" required>
                        <input type="text" name="txtPhone" placeholder="Số điện thoại *" required>
                    </div>
                    <input type="email" name="txtEmail" placeholder="Email" class="full-width">
                    <input type="text" name="txtAddress" placeholder="Địa chỉ *" class="full-width" required>
                    <div class="input-row">
                        <input type="text" name="txtDistrict" placeholder="Quận/Huyện *" required>
                        <input type="text" name="txtCity" placeholder="Tỉnh/Thành phố *" required>
                    </div>
                    <textarea name="txtNote" placeholder="Ghi chú đơn hàng (tùy chọn)" rows="3"></textarea>

                </section>

                <section class="section-box">
                    <h2 class="section-title">Phương thức thanh toán</h2>
                    <div class="payment-list">
                        
                        <label class="payment-item-box">
                            <div class="payment-item-inner">
                                <input type="radio" name="paymentMethod" value="COD" checked>
                                <div class="payment-icon-container">
                                    <i class="fa-solid fa-wallet text-secondary"></i>
                                </div>
                                <div class="payment-info">
                                    <strong class="payment-title">Thanh toán khi nhận hàng (COD)</strong>
                                    <small class="payment-sub">Thanh toán bằng tiền mặt khi nhận hàng</small>
                                </div>
                            </div>
                        </label>

                        <label class="payment-item-box">
                            <div class="payment-item-inner">
                                <input type="radio" name="paymentMethod" value="MOMO">
                                <div class="payment-icon-container">
                                    <i class="fa-solid fa-mobile-screen-button" style="color: #ae2070;"></i>
                                </div>
                                <div class="payment-info">
                                    <strong class="payment-title">Ví MoMo</strong>
                                    <small class="payment-sub">Thanh toán qua ví điện tử MoMo</small>
                                </div>
                            </div>
                        </label>

                        <label class="payment-item-box">
                            <div class="payment-item-inner">
                                <input type="radio" name="paymentMethod" value="ZALOPAY">
                                <div class="payment-icon-container">
                                    <i class="fa-solid fa-credit-card" style="color: #0081ff;"></i>
                                </div>
                                <div class="payment-info">
                                    <strong class="payment-title">ZaloPay</strong>
                                    <small class="payment-sub">Thanh toán qua ví điện tử ZaloPay</small>
                                </div>
                            </div>
                        </label>

                        <label class="payment-item-box">
                            <div class="payment-item-inner">
                                <input type="radio" name="paymentMethod" value="QRCODE">
                                <div class="payment-icon-container">
                                    <i class="fa-solid fa-qrcode" style="color: #7b1fa2;"></i>
                                </div>
                                <div class="payment-info">
                                    <strong class="payment-title">QR Code</strong>
                                    <small class="payment-sub">Quét mã QR để thanh toán</small>
                                </div>
                            </div>
                        </label>
                    </div>

                    <div id="payment-note" class="alert alert-primary border-0 mt-3 mb-0" style="font-size: 0.85rem; background-color: #f0f7ff; color: #004085;">
                        <i class="bi bi-info-circle me-1"></i>
                        <span>Lưu ý: Bạn sẽ được kiểm tra hàng trước khi thanh toán.</span>
                    </div>
                </section>
            </form>
        </div>

        <aside class="col-lg-4">
            <div class="checkout-right">
                <h2 class="section-title">Đơn hàng của bạn</h2>
                <div class="order-summary-list">
                    <c:forEach var="cartItem" items="${cartItemsCheckout}">
                        <div class="book-item-mini d-flex align-items-center gap-3 mb-3">
                            <div class="position-relative">
                                <img src="${cartItem.imageUrl}" alt="Book" style="width: 55px; height: 75px; object-fit: cover;" class="rounded">
                                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary">${cartItem.quantity}</span>
                            </div>
                            <div>
                                <h3 style="font-size: 14px; margin-bottom: 2px;">${cartItem.title}</h3>
                                <span class="text-success fw-bold">
                                    <fmt:setLocale value="vi_VN"/>
                                    <fmt:formatNumber value="${cartItem.price}" type="currency"/>
                                </span>
                                <h4 style="font-size: small; color: #6b7280" >Số lượng: ${cartItem.quantity}</h4>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="price-details mt-4 pt-3 border-top">
                    <div class="d-flex justify-content-between mb-2" style="font-size: 14px;">
                        <span>Tạm tính</span>
                        <span><fmt:formatNumber value="${total}" type="currency"/></span>
                    </div>
                    <div class="d-flex justify-content-between mb-2" style="font-size: 14px;">
                        <span>Phí vận chuyển</span>
                        <span class="text-muted">Miễn phí</span>
                    </div>
                    <div class="d-flex justify-content-between mt-3 pt-3 border-top">
                        <strong class="fs-5">Tổng cộng</strong>
                        <strong class="total-amount fs-5 text-success">
                             <fmt:formatNumber value="${total}" type="currency"/>
                        </strong>
                    </div>
                    <button type="submit" form="checkoutForm" class="btn btn-success w-100 mt-4 py-3 fw-bold">ĐẶT HÀNG NGAY</button>
                </div>
            </div>
        </aside>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/checkout.js"></script>

</body>
</html>