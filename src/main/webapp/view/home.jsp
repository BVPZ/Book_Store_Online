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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chatbot.css">

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
                       <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm sách..." style="border-color: #00a651; box-shadow: none;">
                        <button class="btn search-btn" type="submit"><i class="bi bi-search"></i> Tìm kiếm</button>
                    </div>
                </form>
            </div>
            <div class="col-md-3 d-flex justify-content-end">
                <a href="${pageContext.request.contextPath}/order-history" class="icon-btn"><i class="bi bi-box-seam"></i>Tra cứu đơn hàng</a>
                <a href="cart" class="icon-btn position-relative">
                    <i class="bi bi-cart2"></i>Giỏ hàng
                    <span class="badge bg-danger rounded-pill position-absolute" data-cart-count style="display: none; top: -5px; right: -5px; font-size: 10px; padding: 3px 6px;">0</span>
                </a>

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

<!-- HERO BANNER CAROUSEL -->
<div class="hero-banner">
    <div id="heroBannerCarousel" class="carousel slide" data-bs-ride="carousel" data-bs-interval="4000">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#heroBannerCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#heroBannerCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#heroBannerCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
            <button type="button" data-bs-target="#heroBannerCarousel" data-bs-slide-to="3" aria-label="Slide 4"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <a href="#all-books"><img src="${pageContext.request.contextPath}/images/banner1.png" class="d-block w-100 hero-carousel-img" alt="Banner 1"></a>
            </div>
            <div class="carousel-item">
                <a href="#all-books"><img src="${pageContext.request.contextPath}/images/banner2.png" class="d-block w-100 hero-carousel-img" alt="Banner 2"></a>
            </div>
            <div class="carousel-item">
                <a href="#all-books"><img src="${pageContext.request.contextPath}/images/banner3.png" class="d-block w-100 hero-carousel-img" alt="Banner 3"></a>
            </div>
            <div class="carousel-item">
                <a href="#all-books"><img src="${pageContext.request.contextPath}/images/banner4.png" class="d-block w-100 hero-carousel-img" alt="Banner 4"></a>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#heroBannerCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#heroBannerCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
</div>



<main class="main-layout">

    <aside class="sidebar-wrapper">
        <div class="sidebar-menu">
            <div class="sidebar-header">
                <i class="bi bi-list"></i>
                <span>DANH MỤC SÁCH</span>
            </div>

            <a href="home" class="menu-item ${empty param.cid ? 'active' : ''}" data-category="all">
                <i class="bi bi-book"></i> Tất cả sách
            </a>
            <a href="category?cid=3" class="menu-item ${param.cid == '3' ? 'active' : ''}" data-category="literature">
                <i class="bi bi-flower2"></i> Văn học
            </a>
            <a href="category?cid=1" class="menu-item ${param.cid == '1' ? 'active' : ''}" data-category="mystery">
                <i class="bi bi-shield-lock"></i> Trinh thám
            </a>
            <a href="category?cid=4" class="menu-item ${param.cid == '4' ? 'active' : ''}" data-category="classic">
                <i class="bi bi-gem"></i> Văn học cổ điển
            </a>
            <a href="category?cid=5" class="menu-item ${param.cid == '5' ? 'active' : ''}" data-category="science">
                <i class="bi bi-flask"></i> Khoa học
            </a>
            <a href="category?cid=2" class="menu-item ${param.cid == '2' ? 'active' : ''}" data-category="history">
                <i class="bi bi-clock-history"></i> Lịch sử
            </a>
            <a href="category?cid=6" class="menu-item ${param.cid == '6' ? 'active' : ''}" data-category="romance">
                <i class="bi bi-heart"></i> Lãng mạn
            </a>
            <a href="category?cid=7" class="menu-item ${param.cid == '7' ? 'active' : ''}" data-category="fantasy">
                <i class="bi bi-star"></i> Giả tưởng
            </a>
            <a href="category?cid=8" class="menu-item ${param.cid == '8' ? 'active' : ''}" data-category="business">
                <i class="bi bi-briefcase"></i> Kinh doanh
            </a>
        </div>

        <div class="promo-banner">
            <div class="promo-banner-icon">
                <i class="bi bi-tags-fill"></i>
            </div>
            <h5 class="fw-bold">Ưu Đãi Đặc Biệt</h5>
            <p class="promo-banner-text">Freeship cho đơn hàng trên 300k</p>
            <a href="#" class="btn btn-sm btn-promo-banner">Khám Phá</a>
        </div>
    </aside>


    <section class="content-wrapper">

            <c:choose>
                <c:when test="${not empty searchKeyword}">
                    <div class="product-section">
                        <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
                            <h2 class="section-title">KẾT QUẢ TÌM KIẾM CHO: "${searchKeyword}"</h2>
                           <a href="home" class="btn btn-outline-danger fw-bold px-3 py-1 rounded-pill shadow-sm d-flex align-items-center">
                               <i class="bi bi-x-circle fs-5 me-2"></i> Hủy tìm kiếm
                           </a>
                        </div>

                        <c:if test="${empty searchResults}">
                            <div class="alert alert-warning">
                                Không tìm thấy cuốn sách nào chứa từ khóa "<strong>${searchKeyword}</strong>".
                            </div>
                        </c:if>

                        <c:if test="${not empty searchResults}">
                            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-4 g-3">
                                <c:forEach var="book" items="${searchResults}">
                                    <div class="col">
                                        <div class="product-card">
                                            <div class="product-img-wrap">
                                                <img src="${book.imageUrl}" class="product-img" alt="Book">
                                            </div>
                                            <div class="product-info">
                                                <a href="book-detail?id=${book.bookId}" class="product-title">
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
                        </c:if>
                    </div>
                </c:when>

                <c:otherwise>
                    <c:if test="${empty param.cid}">

                    <div class="product-section">
                        <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
                            <h2 class="section-title">
                                <i class="bi bi-stars text-warning me-2"></i>SÁCH GỢI Ý CHO BẠN
                            </h2>
                        </div>
                        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-4 g-3">
                            <c:forEach var="book" items="${sessionScope.suggested_books}">
                                <c:if test="${book.stock > 0}">
                                    <div class="col">
                                        <div class="product-card" data-book-id="${book.bookId}">
                                            <div class="badge-container">
                                                <span class="badge-new">Gợi Ý</span>
                                            </div>
                                            <div class="product-img-wrap">
                                                <img src="${book.imageUrl}" class="product-img" alt="Book" loading="lazy">
                                                <div class="product-overlay">
                                                    <button class="btn-quick-view" data-bs-toggle="modal" data-bs-target="#quickViewModal" onclick="loadQuickView(${book.bookId})">
                                                        <i class="bi bi-eye"></i> Xem Nhanh
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="product-info">
                                                <a href="book-detail?id=${book.bookId}" class="product-title">
                                                    ${book.title}
                                                </a>

                                                <span class="product-price">
                                                    <fmt:setLocale value="vi_VN"/>
                                                    <fmt:formatNumber value="${book.price}" type="currency"/>
                                                </span>
                                                <div class="product-actions">
                                                    <button class="btn-add-cart" onclick="addToCart(${book.bookId})" title="Thêm vào giỏ">
                                                        <i class="bi bi-cart-plus"></i>
                                                    </button>
                                                    <button class="btn-wishlist" onclick="addToWishlist(${book.bookId})" title="Yêu thích">
                                                        <i class="bi bi-heart"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>

                        <%-- Sách mới lên kệ --%>
                        <div class="product-section">
                            <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
                                <h2 class="section-title">
                                    <i class="bi bi-star text-warning me-2"></i>SÁCH MỚI LÊN KỆ
                                </h2>
                            </div>
                            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-4 g-3">
                                <c:forEach var="book" items="${sessionScope.new_books}">
                                    <c:if test="${book.stock > 0}">
                                        <div class="col">
                                            <div class="product-card" data-book-id="${book.bookId}">
                                                <div class="badge-container">
                                                    <span class="badge-new">Mới</span>
                                                </div>
                                                <div class="product-img-wrap">
                                                    <img src="${book.imageUrl}" class="product-img" alt="Book" loading="lazy">
                                                    <div class="product-overlay">
                                                        <button class="btn-quick-view" data-bs-toggle="modal" data-bs-target="#quickViewModal" onclick="loadQuickView(${book.bookId})">
                                                            <i class="bi bi-eye"></i> Xem Nhanh
                                                        </button>
                                                    </div>
                                                </div>
                                                <div class="product-info">
                                                    <a href="book-detail?id=${book.bookId}" class="product-title">
                                                        ${book.title}
                                                    </a>

                                                    <span class="product-price">
                                                        <fmt:setLocale value="vi_VN"/>
                                                        <fmt:formatNumber value="${book.price}" type="currency"/>
                                                    </span>
                                                    <div class="product-actions">
                                                        <button class="btn-add-cart" onclick="addToCart(${book.bookId})" title="Thêm vào giỏ">
                                                            <i class="bi bi-cart-plus"></i>
                                                        </button>
                                                        <button class="btn-wishlist" onclick="addToWishlist(${book.bookId})" title="Yêu thích">
                                                            <i class="bi bi-heart"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>

                        <%-- Sách bán chạy --%>
                        <div class="product-section">
                            <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
                                <h2 class="section-title">
                                    <i class="bi bi-fire text-danger me-2"></i>SÁCH BÁN CHẠY
                                </h2>
                            </div>
                            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-4 g-3">
                                <c:forEach var="book" items="${sessionScope.best_seller_books}">
                                    <c:if test="${book.stock > 0}">
                                        <div class="col">
                                            <div class="product-card" data-book-id="${book.bookId}">
                                                <div class="badge-container">
                                                    <span class="badge-new" style="background: linear-gradient(135deg, #ff6a00, #ff4c00);">Hot</span>
                                                </div>
                                                <div class="product-img-wrap">
                                                    <img src="${book.imageUrl}" class="product-img" alt="Book" loading="lazy">
                                                    <div class="product-overlay">
                                                        <button class="btn-quick-view" data-bs-toggle="modal" data-bs-target="#quickViewModal" onclick="loadQuickView(${book.bookId})">
                                                            <i class="bi bi-eye"></i> Xem Nhanh
                                                        </button>
                                                    </div>
                                                </div>
                                                <div class="product-info">
                                                    <a href="book-detail?id=${book.bookId}" class="product-title">
                                                        ${book.title}
                                                    </a>

                                                    <span class="product-price">
                                                        <fmt:setLocale value="vi_VN"/>
                                                        <fmt:formatNumber value="${book.price}" type="currency"/>
                                                    </span>
                                                    <div class="product-actions">
                                                        <button class="btn-add-cart" onclick="addToCart(${book.bookId})" title="Thêm vào giỏ">
                                                            <i class="bi bi-cart-plus"></i>
                                                        </button>
                                                        <button class="btn-wishlist" onclick="addToWishlist(${book.bookId})" title="Yêu thích">
                                                            <i class="bi bi-heart"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>

                    <div id="all-books" class="product-section all-books-section mt-5">
                        <h2 class="section-title mb-4" style="font-size: 1.2rem; font-weight: bold; text-transform: uppercase;">
                            <i class="bi bi-collection me-2"></i>
                            <c:choose>
                                <c:when test="${not empty selectedCategory}">
                                    ${selectedCategory.categoryName}
                                </c:when>
                                <c:otherwise>
                                    TẤT CẢ SÁCH
                                </c:otherwise>
                            </c:choose>
                        </h2>
                        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                            <c:forEach var="book" items="${all_books}">
                                <c:if test="${book.stock > 0}">
                                    <div class="col">
                                        <div class="product-card" data-book-id="${book.bookId}">
                                            <div class="product-img-wrap">
                                                <img src="${book.imageUrl}" class="product-img" alt="Book" loading="lazy">
                                                <div class="product-overlay">
                                                    <button class="btn-quick-view" data-bs-toggle="modal" data-bs-target="#quickViewModal" onclick="loadQuickView(${book.bookId})">
                                                        <i class="bi bi-eye"></i> Xem Nhanh
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="product-info">
                                                <a href="book-detail?id=${book.bookId}" class="product-title">
                                                    ${book.title}
                                                </a>
                                               
                                                <span class="product-price">
                                                    <fmt:setLocale value="vi_VN"/>
                                                    <fmt:formatNumber value="${book.price}" type="currency"/>
                                                </span>
                                                <div class="product-actions">
                                                    <button class="btn-add-cart" onclick="addToCart(${book.bookId})" title="Thêm vào giỏ">
                                                        <i class="bi bi-cart-plus"></i>
                                                    </button>
                                                    <button class="btn-wishlist" onclick="addToWishlist(${book.bookId})" title="Yêu thích">
                                                        <i class="bi bi-heart"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

        </section>

</main>

<!-- QUICK VIEW MODAL -->
<div class="modal fade" id="quickViewModal" tabindex="-1" role="dialog" aria-labelledby="quickViewLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="quickViewLabel">Xem Nhanh Sách</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Content will be dynamically loaded -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-success" id="modalAddToCart">
                    <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                </button>
            </div>
        </div>
    </div>
</div>

<!-- NÚT MỞ CHATBOT -->
<div id="chatbot-toggle">
    <i class="bi bi-chat-dots-fill"></i>
</div>

<!-- KHUNG CHATBOT -->
<div id="chatbot-box">
    <div id="chatbot-header">
        <span><i class="bi bi-robot me-2"></i>Chat hỗ trợ</span>
        <span id="chatbot-close" style="cursor: pointer;">✖</span>
    </div>
    <div id="chatbot-messages"></div>
    <div id="chatbot-input">
        <input type="text" id="chat-input" placeholder="Nhập tin nhắn...">
        <button onclick="sendMessage()"><i class="bi bi-send-fill"></i></button>
    </div>
</div>

<jsp:include page="footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/home.js"></script>
<script src="${pageContext.request.contextPath}/js/footer.js"></script>
</body>
</html>