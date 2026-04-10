<%@ page import="com.bookshop.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart_style.css">

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
                        <input type="text" class="form-control" placeholder="Tìm kiếm sách, tác giả..." style="border-color: #00a651; box-shadow: none;">
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
              <a href="${pageContext.request.contextPath}/recently-viewed" class="nav-link-custom border-start ms-2 ps-3">Sản phẩm đã xem</a>
            </div>
            <div class="d-flex gap-4">
                <span class="nav-promo"><i class="bi bi-truck me-1"></i> Ship COD Trên Toàn Quốc</span>
                <span class="nav-promo orange"><i class="bi bi-gift me-1"></i> Free Ship Đơn Hàng Trên 300k</span>
            </div>
        </div>
    </div>
</nav>
<main class="container mt-4">

    <!-- GIỎ HÀNG TRỐNG -->
    <c:if test="${empty cartItems}">
        <div class="empty-cart text-center">
            <i class="bi bi-bag"></i>
            <h3>Giỏ hàng trống</h3>
            <p>Hãy thêm sách vào giỏ hàng để tiếp tục mua sắm</p>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-success">
                Tiếp tục mua sắm
            </a>
        </div>
    </c:if>


    <!-- GIỎ HÀNG -->
    <c:if test="${not empty cartItems}">
        <form action="checkout" method="post">
            <div class="row">

                <div class="col-lg-8">

                    <div class="cart-header d-flex justify-content-between align-items-center">
                        <h4>Giỏ hàng của bạn có (${cartItems.size()} sản phẩm)</h4>

                        <label>
                            <input type="checkbox" id="checkAll"> Chọn tất cả
                        </label>
                    </div>

                    <c:forEach var="cartItem" items="${cartItems}">
                        <div class="cart-item d-flex align-items-center">

                            <input type="checkbox" class="item-check me-3"
                                   data-price="${cartItem.price}" name = "selectedItems" value="${cartItem.bookId},${cartItem.quantity},${cartItem.cartItemId}">

                            <img src="${cartItem.imageUrl}" class="cart-img">

                            <div class="flex-grow-1 ms-3">
                                <h5>${cartItem.title}</h5>
                                <small class="text-muted">${cartItem.author}</small>
                                <div class="price">
                                        ${cartItem.price}đ
                                </div>
                            </div>
                            <!-- quantity -->
                            <div class="qty-box">
                                <button class="qty-btn minus">-</button>
                                <input type="text" value="${cartItem.quantity}" class="qty-input">
                                <button class="qty-btn plus">+</button>
                            </div>
                            <!-- delete -->
                            <a href="remove-from-cart?id=${cartItem.cartItemId}" class="delete-btn">
                                <i class="bi bi-trash"></i>
                            </a>
                        </div>
                    </c:forEach>

                </div>
                <!-- RIGHT -->
                <div class="col-lg-4">
                    <div class="summary-box">

                        <h5>Tóm tắt đơn hàng</h5>

                        <div class="row-line">
                            <span>Đã chọn</span>
                            <span id="selectedCount">0 sản phẩm</span>
                        </div>

                        <div class="row-line">
                            <span>Tạm tính</span>
                            <span id="subtotal">0đ</span>
                        </div>

                        <div class="row-line text-success">
                            <span>Phí vận chuyển</span>
                            <span>Miễn phí</span>
                        </div>

                        <hr>

                        <div class="total">
                            <span>Tổng cộng</span>
                            <span id="totalPrice">0đ</span>
                        </div>

                        <input type="submit" class="btn btn-success w-100 mt-3" value="Đặt hàng">
                        <div class="text-danger text-center mt-3">
                            Xóa toàn bộ giỏ hàng
                        </div>

                        <div class="policy">
                            ✓ Miễn phí vận chuyển toàn quốc <br>
                            ✓ Đổi trả trong 7 ngày <br>
                            ✓ Thanh toán khi nhận hàng
                        </div>

                    </div>
                </div>

            </div>
        </form>
    </c:if>

</main>
<script src="js/cart.js"></script>
</body>