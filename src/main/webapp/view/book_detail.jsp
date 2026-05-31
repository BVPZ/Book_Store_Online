<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                        <input type="text" class="form-control" name="keyword"
                               placeholder="Tìm kiếm sách..."
                               style="border-color: #00a651; box-shadow: none;">
                        <button class="btn search-btn" type="submit">
                            <i class="bi bi-search"></i> Tìm kiếm
                        </button>
                    </div>
                </form>
            </div>
            <div class="col-md-3 d-flex justify-content-end">
                <a href="${pageContext.request.contextPath}/order-history" class="icon-btn">
                    <i class="bi bi-box-seam"></i>Tra cứu đơn hàng
                </a>
                <a href="cart" class="icon-btn"><i class="bi bi-cart2"></i>Giỏ hàng</a>
                <%
                    User user = (User) session.getAttribute("user");
                    if (user == null) {
                %>
                <a href="login" class="icon-btn"><i class="bi bi-person"></i>Đăng nhập</a>
                <%
                } else {
                %>
                <a href="profile" class="icon-btn"><i class="bi bi-person"></i>Tài khoản</a>
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
                <a href="#" class="nav-link-custom fw-bold">
                    <i class="bi bi-list me-2"></i> DANH MỤC SÁCH
                </a>
                <a href="${pageContext.request.contextPath}/recently-viewed"
                   class="nav-link-custom border-start ms-2 ps-3">Sản phẩm đã xem</a>
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
                <c:forEach var="g" items="${book.genres}" varStatus="status">
                    ${g} <c:if test="${!status.last}">, </c:if>
                </c:forEach>
            </div>
            <div class="author">
                Tác giả: <a href="author?id=${book.authorId}"
                            style="text-decoration:none; color:#00a651;">${book.authorName}</a>
            </div>

            <div class="d-flex align-items-center my-2">
                <c:forEach begin="1" end="5" var="i">
                    <i class="bi ${i <= avgRating ? 'bi-star-fill' : 'bi-star'}"
                       style="color:#ffc107; font-size:18px;"></i>
                </c:forEach>
                <span class="ms-2 text-muted fw-semibold">
                            <fmt:formatNumber value="${avgRating}" maxFractionDigits="1"/> / 5
                        </span>
                <span class="ms-1 text-muted">(${reviewCount} đánh giá)</span>
            </div>

            <div class="desc-text">Hàng còn lại: ${book.stock}</div>
            <div class="price">${book.getFormattedPrice()}</div>
            <div class="desc-header">Mô tả sản phẩm</div>
            <div class="desc-text">${book.description}</div>

            <c:choose>
                <c:when test="${book.stock > 0}">
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/checkout?id=${book.bookId}">
                            <button class="btn btn-buy-now">Mua ngay</button>
                        </a>
                        <form action="addtocart" method="post">
                            <input type="hidden" name="bookId" value="${book.bookId}">
                            <button class="btn btn-add-cart" type="submit">
                                <i class="fas fa-shopping-cart"></i> Thêm vào giỏ
                            </button>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <p style="color:red;">Hết hàng</p>
                </c:otherwise>
            </c:choose>

            <div class="features-container">
                <div class="feature-item"><i class="fas fa-box"></i> <span>Hàng chính hãng</span></div>
                <div class="feature-item"><i class="fas fa-truck"></i> <span>Giao hàng toàn quốc</span></div>
                <div class="feature-item"><i class="fas fa-shield-alt"></i> <span>Đổi trả 7 ngày</span></div>
            </div>
        </div>
    </div>

    <div class="related-section">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h4 class="fw-bold text-dark mb-0">Sách cùng thể loại</h4>
            <div class="flex-grow-1 ms-3 border-bottom" style="opacity: 0.1;"></div>
        </div>
        <div class="row row-cols-2 row-cols-md-4 g-4">
            <c:forEach items="${relatedBooks}" var="rb">
                <div class="col">
                    <div class="card h-100 related-card">
                        <a href="book-detail?id=${rb.bookId}" class="p-3 text-center">
                            <img src="${rb.imageUrl}" class="card-img-top" alt="${rb.title}"
                                 style="height: 180px; object-fit: contain;">
                        </a>
                        <div class="card-body p-3">
                            <h6 class="card-title text-dark fw-bold text-truncate-2 mb-2">${rb.title}</h6>
                            <p class="text-danger fw-bold mb-0">${rb.price} VNĐ</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="review-section" style="width:95%; max-width:1200px; margin: 30px auto;">
        <div class="d-flex align-items-center mb-4">
            <h4 class="fw-bold text-dark mb-0">Đánh giá sản phẩm</h4>
            <div class="flex-grow-1 ms-3 border-bottom" style="opacity: 0.1;"></div>
        </div>

        <div class="d-flex align-items-center mb-4 p-3 bg-white rounded shadow-sm"
             style="width: fit-content;">
            <div class="text-center me-4">
                <div style="font-size:48px; font-weight:bold; color:#ffc107;">
                    <fmt:formatNumber value="${avgRating}" maxFractionDigits="1"/>
                </div>
                <div>
                    <c:forEach begin="1" end="5" var="i">
                        <i class="bi ${i <= avgRating ? 'bi-star-fill' : 'bi-star'}"
                           style="color:#ffc107; font-size:22px;"></i>
                    </c:forEach>
                </div>
                <div class="text-muted mt-1">${reviewCount} đánh giá</div>
            </div>
        </div>

        <c:if test="${not empty sessionScope.reviewSuccess}">
            <div class="alert alert-success alert-dismissible fade show">
                <i class="bi bi-check-circle me-2"></i>${sessionScope.reviewSuccess}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="reviewSuccess" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.reviewError}">
            <div class="alert alert-danger alert-dismissible fade show">
                <i class="bi bi-exclamation-circle me-2"></i>${sessionScope.reviewError}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="reviewError" scope="session"/>
        </c:if>

        <c:if test="${canReview}">
            <div class="card mb-4 shadow-sm">
                <div class="card-body">
                    <h5 class="fw-bold mb-3">
                        <i class="bi bi-pencil-square me-2 text-success"></i>Viết đánh giá của bạn
                    </h5>
                    <form action="review" method="POST">
                        <input type="hidden" name="bookId" value="${book.bookId}">
                        <div class="mb-3">
                            <label class="form-label fw-bold">Chọn số sao:</label>
                            <div class="star-rating d-flex gap-2">
                                <c:forEach begin="1" end="5" var="i">
                                    <input type="radio" name="rating" value="${i}"
                                           id="star${i}" required style="display:none">
                                    <label for="star${i}"
                                           class="bi bi-star-fill star-label"
                                           data-value="${i}"
                                           style="font-size:32px; color:#ccc; cursor:pointer; transition: color 0.2s;"></label>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold">Bình luận:</label>
                            <textarea name="comment" class="form-control" rows="3"
                                      placeholder="Chia sẻ cảm nhận của bạn về cuốn sách này..."></textarea>
                        </div>
                        <button type="submit" class="btn btn-success px-4">
                            <i class="bi bi-send me-1"></i>Gửi đánh giá
                        </button>
                    </form>
                </div>
            </div>
        </c:if>

        <c:if test="${empty sessionScope.user}">
            <div class="alert alert-info mb-4">
                <i class="bi bi-info-circle me-2"></i>
                <a href="login" class="text-decoration-none fw-bold">Đăng nhập</a>
                để đánh giá sản phẩm. Chỉ người mua hàng thành công mới được đánh giá.
            </div>
        </c:if>

        <c:choose>
            <c:when test="${empty reviews}">
                <div class="text-center py-5 text-muted">
                    <i class="bi bi-chat-square-text" style="font-size:48px;"></i>
                    <p class="mt-3 fs-5">Chưa có đánh giá nào. Hãy là người đầu tiên!</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="review" items="${reviews}">
                    <div class="card mb-3 shadow-sm">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start">
                                <div class="d-flex align-items-center mb-2">
                                    <div class="rounded-circle bg-success text-white d-flex
                                                 align-items-center justify-content-center me-2"
                                         style="width:40px; height:40px; font-size:18px; font-weight:bold;">
                                            ${review.reviewerName.substring(0,1).toUpperCase()}
                                    </div>
                                    <div>
                                        <strong>${review.reviewerName}</strong>
                                        <div>
                                            <c:forEach begin="1" end="5" var="i">
                                                <i class="bi ${i <= review.rating ? 'bi-star-fill' : 'bi-star'}"
                                                   style="color:#ffc107; font-size:14px;"></i>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <small class="text-muted">
                                    <fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                </small>
                            </div>
                            <c:if test="${not empty review.comment}">
                                <p class="mb-0 text-secondary ms-1">${review.comment}</p>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

    <div id="toast" class="toast">Thêm vào giỏ hàng thành công!</div>

    <script>
        function showToast() {
            const toast = document.getElementById("toast");
            toast.classList.add("show");
            setTimeout(() => toast.classList.remove("show"), 3000);
        }
    </script>
    <script>
        <% String msg = (String) session.getAttribute("message"); %>
        <% if ("success".equals(msg)) { %>
        showToast();
        <% } %>
    </script>
    <% session.removeAttribute("message");%>

    <script>
        let selectedRating = 0;
        const starLabels = document.querySelectorAll('.star-label');
        starLabels.forEach(label => {
            const val = parseInt(label.getAttribute('data-value'));
            label.addEventListener('mouseover', () => {
                starLabels.forEach((l, i) => {
                    l.style.color = i < val ? '#ffc107' : '#ccc';
                });
            });
            label.addEventListener('mouseout', () => {
                starLabels.forEach((l, i) => {
                    l.style.color = i < selectedRating ? '#ffc107' : '#ccc';
                });
            });
            label.addEventListener('click', () => {
                selectedRating = val;
                document.getElementById('star' + val).checked = true;
                starLabels.forEach((l, i) => {
                    l.style.color = i < val ? '#ffc107' : '#ccc';
                });
            });
        });
    </script>
</main>

<jsp:include page="footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>