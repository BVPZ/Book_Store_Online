<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.bookshop.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${author.authorName} - Nhà Sách Tri Thức</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/author_detail.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        .main-layout {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .back-container {
            position: static !important;
            display: inline-flex !important;
            font-size: 16px !important;
        }

        .search-btn { background-color: #00a651; color: white; padding: 0 20px; border: none; }
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
                <a href="#" class="nav-link-custom border-start ms-2 ps-3">Sản phẩm đã xem</a>
            </div>
            <div class="d-flex gap-4">
                <span class="nav-promo"><i class="bi bi-truck me-1"></i> Ship COD Trên Toàn Quốc</span>
                <span class="nav-promo orange"><i class="bi bi-gift me-1"></i> Free Ship Đơn Hàng Trên 300k</span>
            </div>
        </div>
    </div>
</nav>

<main class="main-layout mt-4">
    <div class="mb-3 text-start" style="width: 95%; max-width: 1200px;">
        <a href="javascript:history.back()" class="text-decoration-none text-secondary">
            <i class="fas fa-arrow-left"></i> Quay lại
        </a>
    </div>

    <!-- Author Info Card -->
    <div class="author-detail-card">
        <div class="author-icon-container">
            <i class="fas fa-book-open"></i>
        </div>
        <div class="author-info-block">
            <div class="author-name">${author.authorName}</div>
            <div class="author-role">Tác giả</div>
            <div class="author-bio">
                <c:choose>
                    <c:when test="${not empty author.bio}">
                        ${author.bio}
                    </c:when>
                    <c:otherwise>
                        ${author.authorName} là một trong những tác giả nổi tiếng với ${author_books.size()} tác phẩm trong thư viện của chúng tôi.
                        <br><br>
                        Với đánh giá trung bình ${average_rating} sao từ ${total_review_count} lượt đánh giá, các tác phẩm của ${author.authorName} đã nhận được sự yêu thích từ độc giả khắp nơi.
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="author-stats-container">
                <div class="author-stat-box">
                    <div class="author-stat-label">Số lượng sách</div>
                    <div class="author-stat-value">${author_books.size()}</div>
                </div>
                <div class="author-stat-box">
                    <div class="author-stat-label">Đánh giá trung bình</div>
                    <div class="author-stat-value">
                        <i class="fas fa-star"></i> ${average_rating}
                    </div>
                </div>
                <div class="author-stat-box">
                    <div class="author-stat-label">Tổng lượt đánh giá</div>
                    <div class="author-stat-value">${total_review_count}</div>
                </div>
            </div>
        </div>
    </div>

    <!-- Books List -->
    <div class="books-section-title">
        Tất cả sách của ${author.authorName} (${author_books.size()})
    </div>

    <div class="books-grid">
        <c:forEach var="b" items="${author_books}">
            <a href="book-detail?id=${b.bookId}" class="book-card">
                <div class="book-card-image-wrapper">
                    <!-- Added a hardcoded tag for the genre based on the design mockup if getGenres isn't set -->
                    <span class="book-genre-badge">Văn học</span>
                    <img src="${b.imageUrl}" class="book-card-image" alt="Book Cover">
                </div>
                <div class="book-card-info">
                    <div class="book-card-title">${b.title}</div>
                    <div class="book-card-rating">
                        <i class="fas fa-star"></i> ${b.rating} <span>(${b.reviewCount})</span>
                    </div>
                    <div class="book-card-desc">
                        <c:choose>
                            <c:when test="${not empty b.description}">
                                ${b.description}
                            </c:when>
                            <c:otherwise>
                                Tác phẩm nổi tiếng của ${author.authorName}
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="book-card-price">${b.getFormattedPrice()}</div>
                </div>
            </a>
        </c:forEach>
    </div>
</main>
</body>
</html>
