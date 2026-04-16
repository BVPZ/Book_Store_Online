<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.bookshop.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trạng thái đơn hàng - Nhà Sách Tri Thức</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/book_detail.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        .main-layout {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .product-detail-card {
            width: 95% !important;
            max-width: 1200px !important;
            gap: 60px !important;
            padding: 50px !important;
        }

        .back-container {
            position: static !important;
            display: inline-flex !important;
            font-size: 16px !important;
        }

        .action-buttons {
            display: flex !important;
            gap: 15px !important;
            width: 100% !important;
        }
        .action-buttons > button,
        .action-buttons > form {
            flex: 1 !important;
        }
        .action-buttons form button {
            width: 100% !important;
        }
        .search-btn { background-color: var(--primary-green); color: white; padding: 0 20px; border: none; }
        .search-btn:hover { background-color: #008a43; }
    </style>
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
                        <input type="text" class="form-control" name="keyword" placeholder="Tìm kiếm sách..." style="border-color: #00a651; box-shadow: none;">
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
<main class="success-page-wrapper">
    <div class="success-card">
        <div class="icon-wrapper">
            <i class="bi bi-check-circle-fill"></i>
        </div>
        <h1 class="success-title">Đặt Hàng Thành Công!</h1>
        <p class="success-desc">Cảm ơn bạn đã tin tưởng và mua sắm tại Nhà Sách Tri Thức. Đơn hàng của bạn đang được xử lý và sẽ sớm được giao đến bạn.</p>
        
        <div class="btn-group-custom mt-4">
            <a href="order-history" class="btn-custom btn-home text-decoration-none">
                <i class="bi bi-list-ul"></i> Xem đơn hàng
            </a>
            <a href="home" class="btn-custom btn-order text-decoration-none">
                <i class="bi bi-house-door"></i> Trở về trang chủ
            </a>
        </div>
    </div>
</main>

<style>
    .success-page-wrapper {
        min-height: calc(100vh - 160px); /* Adjust based on header */
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 40px 20px;
        background-color: #f4f7f6;
    }

    .success-card {
        background: white;
        border-radius: 20px;
        padding: 50px 40px;
        box-shadow: 0 10px 40px rgba(0, 0, 0, 0.08);
        text-align: center;
        max-width: 500px;
        width: 100%;
        position: relative;
        overflow: hidden;
        animation: slideUpFade 0.6s ease-out forwards;
    }

    .success-card::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 6px;
        background: linear-gradient(90deg, #00a651, #00d26a);
    }

    .icon-wrapper {
        width: 90px;
        height: 90px;
        background: rgba(0, 166, 81, 0.1);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto 30px;
        position: relative;
    }

    .icon-wrapper .bi-check-circle-fill {
        font-size: 45px;
        color: #00a651;
        z-index: 2;
        animation: scaleIn 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.275) 0.2s both;
    }

    .icon-wrapper::after {
        content: '';
        position: absolute;
        width: 100%;
        height: 100%;
        border-radius: 50%;
        box-shadow: 0 0 0 15px rgba(0, 166, 81, 0.05);
        animation: pulseRing 1.5s infinite cubic-bezier(0.215, 0.61, 0.355, 1);
    }

    .success-title {
        font-size: 28px;
        font-weight: 700;
        color: #2b3445;
        margin-bottom: 15px;
        letter-spacing: -0.5px;
    }

    .success-desc {
        color: #7d879c;
        font-size: 16px;
        line-height: 1.6;
        margin-bottom: 40px;
    }

    .btn-group-custom {
        display: flex;
        gap: 15px;
        justify-content: center;
    }

    .btn-custom {
        padding: 12px 24px;
        border-radius: 10px;
        font-weight: 600;
        font-size: 15px;
        transition: all 0.3s ease;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        gap: 8px;
    }

    .btn-home {
        background: #f3f5f9;
        color: #2b3445;
        border: none;
    }

    .btn-home:hover {
        background: #e3e8f0;
        transform: translateY(-2px);
    }

    .btn-order {
        background: linear-gradient(135deg, #00a651 0%, #008a43 100%);
        color: white;
        border: none;
        box-shadow: 0 4px 15px rgba(0, 166, 81, 0.3);
    }

    .btn-order:hover {
        background: linear-gradient(135deg, #008a43 0%, #007035 100%);
        transform: translateY(-2px);
        box-shadow: 0 6px 20px rgba(0, 166, 81, 0.4);
        color: white;
    }

    @keyframes slideUpFade {
        from { opacity: 0; transform: translateY(30px); }
        to { opacity: 1; transform: translateY(0); }
    }

    @keyframes scaleIn {
        from { opacity: 0; transform: scale(0.5); }
        to { opacity: 1; transform: scale(1); }
    }

    @keyframes pulseRing {
        0% { transform: scale(0.8); box-shadow: 0 0 0 0 rgba(0, 166, 81, 0.2); }
        70% { transform: scale(1.3); box-shadow: 0 0 0 20px rgba(0, 166, 81, 0); }
        100% { transform: scale(1.3); box-shadow: 0 0 0 0 rgba(0, 166, 81, 0); }
    }
</style>

<jsp:include page="footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>