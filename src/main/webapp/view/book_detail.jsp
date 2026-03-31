<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.bookshop.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${book.title} - Nhà Sách Tri Thức</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/book_detail.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
</head>
<body>

<header class="top-header">
    <div class="container-fluid px-4">
        <div class="row align-items-center">
            <a href="home" style="text-decoration: none">
            <div class="col-md-3 d-flex align-items-center">
                    <div class="bg-success text-white rounded p-2 me-2" style="font-weight:bold; font-size:18px;">
                        <i class="bi bi-book"></i>
                    </div>
                    <div>
                            <div class="logo-text">Nhà Sách Tri Thức</div>
                            <div class="logo-sub">Đọc sách - Mở tương lai</div>
                    </div>
            </div>
            </a>
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
                <a href="#" class="icon-btn"><i class="bi bi-cart2"></i>Giỏ hàng</a>

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
                <a href="#" class="nav-link-custom border-start ms-2 ps-3">Sản phẩm đã xem</a>
            </div>
            <div class="d-flex gap-4">
                <span class="nav-promo"><i class="bi bi-truck me-1"></i> Ship COD Trên Toàn Quốc</span>
                <span class="nav-promo orange"><i class="bi bi-gift me-1"></i> Free Ship Đơn Hàng Trên 300k</span>
            </div>
        </div>
    </div>
</nav>
<main class="main-layout">
<div style="width: 100%; max-width: 1000px;">
        <a href="${pageContext.request.contextPath}/home" class="back-container">
            <i class="fas fa-arrow-left"></i> Quay lại
        </a>
    </div>

    <div class="product-detail-card">

        <div class="image-column">
            <img src="${book.imageUrl}" alt="${book.title}" class="product-image">
        </div>

        <div class="info-column">
            <div class="genre">
                <c:forEach var="g" items="${book.genres}">
                    ${g}
                </c:forEach>
            </div>

            <h1 class="title">${book.title}</h1>

            <div class="author">Tác giả: ${book.authorId}</div>
            <!--
            <div class="rating-container">
                <div class="stars">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star-half-alt"></i>
                </div>

            <span class="review-text">${book.rating} (${book.reviewCount} đánh giá)</span>
            </div>-->

        <div class="price">${book.getFormattedPrice()}</div>

            <div class="desc-header">Mô tả sản phẩm</div>
            <div class="desc-text">${book.description}</div>

            <div class="action-buttons">
                <button class="btn btn-buy-now">Mua ngay</button>

                <button class="btn btn-add-cart">
                    <i class="fas fa-shopping-cart"></i> Thêm vào giỏ
                </button>
            </div>

            <div class="features-container">
                <div class="feature-item">
                    <i class="fas fa-box"></i> <span>Hàng chính hãng</span>
                </div>
                <div class="feature-item">
                    <i class="fas fa-truck"></i> <span>Giao hàng toàn quốc</span>
                </div>
                <div class="feature-item">
                    <i class="fas fa-shield-alt"></i> <span>Đổi trả 7 ngày</span>
                </div>
            </div>

        </div> </div>

</main>

</body>
</html>