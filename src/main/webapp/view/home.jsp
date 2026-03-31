<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.bookshop.model.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Nhà Sách Tri Thức - Trang Chủ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">

</head>
<body>

<header class="top-header">
    <div class="container-fluid px-4">
        <div class="row align-items-center">
            <a href ="home" style="text-decoration: none ">
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

    <aside class="sidebar-wrapper">
        <div class="sidebar-menu">
            <div class="px-3 py-2 fw-bold text-secondary" style="font-size: 13px;"><i class="bi bi-list"></i> DANH MỤC SÁCH</div>
            <a href="#" class="menu-item active">Tất cả sách</a>
            <a href="#" class="menu-item">Văn học</a>
            <a href="#" class="menu-item">Trinh thám</a>
            <a href="#" class="menu-item">Văn học cổ điển</a>
            <a href="#" class="menu-item">Khoa học</a>
            <a href="#" class="menu-item">Lịch sử</a>
            <a href="#" class="menu-item">Lãng mạn</a>
            <a href="#" class="menu-item">Giả tưởng</a>
            <a href="#" class="menu-item">Kinh doanh</a>
        </div>
        <div class="promo-banner">
            <div class="fs-2 mb-1"><i class="bi bi-tags-fill"></i></div>
            <h5 class="fw-bold fs-6">Ưu đãi đặc biệt</h5>
            <p class="mb-0 fs-7" style="font-size: 12px;">Freeship cho đơn hàng trên 300k</p>
        </div>
    </aside>

    <section class="content-wrapper">

       <div class="product-section">
           <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
               <h2 class="section-title">SÁCH MỚI LÊN KỆ</h2>
               <a href="#" class="view-more">Xem thêm <i class="bi bi-chevron-right"></i></a>
           </div>
           <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-4 g-3">

               <c:forEach var="book" items="${sessionScope.new_books}">
                   <div class="col">
                       <div class="product-card">
                           <div class="product-img-wrap">
                               <img src="${book.imageUrl}" class="product-img" alt="Book">
                           </div>
                           <div class="product-info">
                               <a href="book-detail?id=${book.id}" class="product-title">
                                       ${book.title}
                               </a>
                               <span class="product-price">
                                   <fmt:setLocale value="vi_VN"/>
                                   <fmt:formatNumber value="${book.price}" type="currency"/>
                               </span>

                           </div>
                       </div>
                   </div>
               </c:forEach>
           </div>
       </div>

       <div class="product-section">
           <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
               <h2 class="section-title">SÁCH BÁN CHẠY</h2>
               <a href="#" class="view-more">Xem thêm <i class="bi bi-chevron-right"></i></a>
           </div>
           <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-4 g-3">
               <c:forEach var="book" items="${sessionScope.best_seller_books}">
                   <div class="col">
                       <div class="product-card">
                           <div class="product-img-wrap">
                               <img src="${book.imageUrl}" class="product-img" alt="Book">
                           </div>
                           <div class="product-info">
                               <a href="book-detail?id=${book.id}" class="product-title">
                                       ${book.title}
                               </a>
                               <span class="product-price">
                                   <fmt:setLocale value="vi_VN"/>
                                   <fmt:formatNumber value="${book.price}" type="currency"/>
                               </span>

                           </div>
                       </div>
                   </div>
               </c:forEach>
           </div>
       </div>

        <div class="product-section all-books-section mt-5">
            <h2 class="section-title mb-4" style="font-size: 1.2rem; font-weight: bold; text-transform: uppercase;">Tất cả sách</h2>

            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                <c:forEach var="book" items="${sessionScope.all_books}">
                    <div class="col">
                        <div class="product-card">
                            <div class="product-img-wrap">
                                <img src="${book.imageUrl}" class="product-img" alt="Book">
                            </div>
                            <div class="product-info">
                                <a href="book-detail?id=${book.id}" class="product-title">
                                        ${book.title}
                                </a>
                                <span class="product-price">
                                   <fmt:setLocale value="vi_VN"/>
                                   <fmt:formatNumber value="${book.price}" type="currency"/>
                               </span>

                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>


</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>