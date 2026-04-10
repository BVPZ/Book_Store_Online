<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.bookshop.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sản phẩm đã xem - Nhà Sách Tri Thức</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
    <style>
        .history-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 0;
            border-bottom: 1px solid #eee;
            margin-bottom: 30px;
        }
        .section-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        .history-icon {
            background-color: #00a651;
            color: white;
            width: 45px;
            height: 45px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 20px;
        }
        .btn-clear {
            color: #dc3545;
            border: 1px solid #dc3545;
            background: transparent;
            padding: 8px 20px;
            border-radius: 5px;
            transition: all 0.3s;
        }
        .btn-clear:hover {
            background: #dc3545;
            color: white;
        }
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
                       <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm sách..."
style="border-color: #00a651; box-shadow: none;">
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
<main class="container my-5">
    <div class="history-header">
        <div class="section-info">
            <div class="history-icon">
                <i class="bi bi-eye"></i>
            </div>
            <div>
                <h2 class="m-0 fw-bold" style="font-size: 24px;">Sản phẩm đã xem</h2>
                <p class="text-muted m-0">${recentBooks.size()} sản phẩm trong lịch sử</p>
            </div>
        </div>

        <c:if test="${not empty recentBooks}">
            <form action="clear-history" method="POST" onsubmit="return confirm('Bạn có chắc muốn xóa toàn bộ lịch sử xem?')">
                <button type="submit" class="btn-clear">
                    <i class="bi bi-trash me-2"></i>Xóa tất cả
                </button>
            </form>
        </c:if>
    </div>

    <c:choose>
        <c:when test="${empty recentBooks}">
            <div class="text-center py-5">
                <i class="bi bi-clock-history" style="font-size: 50px; color: #ccc;"></i>
                <h4 class="mt-3 text-muted">Bạn chưa xem sản phẩm nào</h4>
                <a href="home" class="btn btn-success mt-3">Tiếp tục mua sắm</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
                <c:forEach var="book" items="${recentBooks}">
                    <div class="col">
                        <div class="product-card">
                            <div class="product-img-wrap">
                                <img src="${book.imageUrl}" class="product-img" alt="${book.title}">
                            </div>
                            <div class="product-info">
                                <a href="book-detail?id=${book.bookId}" class="product-title">${book.title}</a>
                                <div class="d-flex justify-content-between align-items-center mt-2">
                                    <span class="product-price text-success fw-bold">
                                        <fmt:setLocale value="vi_VN"/>
                                        <fmt:formatNumber value="${book.price}" type="currency"/>
                                    </span>
                                </div>
                                <form action="addtocart" method="POST" class="mt-2">
                                    <input type="hidden" name="bookId" value="${book.bookId}">
                                    <button type="submit" class="btn btn-sm btn-outline-success w-100">
                                        <i class="bi bi-cart-plus me-1"></i> Thêm vào giỏ
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>