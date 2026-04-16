<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.bookshop.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${book.title} - Nhà Sách Tri Thức</title>
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

<main class="main-layout">
   <div class="mb-3 text-start" style="width: 95%; max-width: 1200px;">
           <a href="${pageContext.request.contextPath}/home" class="text-decoration-none text-secondary">
               <i class="fas fa-arrow-left"></i> Quay lại
           </a>
       </div>

    <div class="product-detail-card">
        <div class="image-column">
            <img src="${book.imageUrl}" alt="${book.title}" class="product-image">
        </div>

        <div class="info-column">
            <div class="title" style="font-size:x-large">${book.title}</div>
            <div class="genre">
                <c:forEach var="g" items="${book.genres} " varStatus="status">
                    ${g} <c:if test="${!status.last}">, </c:if>
                </c:forEach>
            </div>

            <div class="author">Tác giả: <a href="author?id=${book.authorId}" style="text-decoration:none; color:#00a651;">${book.authorName}</a></div>
            <!--
            <div style="color: #ffc107; font-size: 15px; margin-top: 10px; margin-bottom: 15px; display: flex; align-items: center;">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star-half-alt"></i>
                <span style="color: #6c757d; font-size: 14px; margin-left: 8px;">
                    ${book.rating} (${book.reviewCount} đánh giá)
                </span>
            </div>
            -->
            <div class="desc-text">Hàng còn lại: ${book.description}</div>
            <div class="price">${book.getFormattedPrice()}</div>
            <div class="desc-header">Mô tả sản phẩm</div>
            <div class="desc-text">${book.description}</div>

            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/checkout?id=${book.bookId}">
                <button class="btn btn-buy-now">Mua ngay</button>
                </a>
                <form action="addtocart" method="Post">
                    <button class="btn btn-add-cart" type="submit">
                        <i class="fas fa-shopping-cart"></i> Thêm vào giỏ
                    </button>
                </form>
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
        </div>
    </div>
    <div id="toast" class="toast">
        Thêm vào giỏ hàng thành công!
    </div>
    <script>
        function showToast() {
            const toast = document.getElementById("toast");
            toast.classList.add("show");

            setTimeout(() => {
                toast.classList.remove("show");
            }, 3000);
        }
    </script>
    <script>
        <% String msg = (String) session.getAttribute("message"); %>
        <% if ("success".equals(msg)) { %>
        showToast();
        <% } %>
    </script>

    <%
        session.removeAttribute("message");
    %>
</main>


<jsp:include page="footer.jsp" />
</body>
</html>