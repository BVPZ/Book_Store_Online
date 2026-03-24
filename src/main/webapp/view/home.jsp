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
            <div class="col-md-3 d-flex align-items-center">
                <div class="bg-success text-white rounded p-2 me-2" style="font-weight:bold; font-size:18px;">
                    <i class="bi bi-book"></i>
                </div>
                <div>
                    <div class="logo-text">Nhà Sách Tri Thức</div>
                    <div class="logo-sub">Đọc sách - Mở tương lai</div>
                </div>
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
               <div class="col">
                   <div class="product-card">
                       <div class="product-img-wrap"><img src="https://m.media-amazon.com/images/I/81PndgdGCXL._AC_UF1000,1000_QL80_.jpg" class="product-img" alt="Book"></div>
                       <div class="product-info">
                           <a href="book-detail?id=12" class="product-title">The Correspondent </a>
                           <p class="product-price">299.000đ</p>
                       </div>
                   </div>
               </div>
               <div class="col"><div class="product-card"><div class="product-img-wrap"><img src="https://bizweb.dktcdn.net/thumb/1024x1024/100/576/749/products/38f61786-16ea-4a59-8f90-a2b5dd8978c4.jpg?v=1773741971217" class="product-img"></div><div class="product-info">
                   <a href="book-detail?id=13" class="product-title">Ngày Nay Có Một Ông Trời</a><p class="product-price">259.000đ</p></div></div></div>
               <div class="col"><div class="product-card"><div class="product-img-wrap"><img src="https://cdn1.fahasa.com/media/catalog/product/z/7/z7012700093197_a8fafde4c12b2c28a893345d31d1beb2.jpg" class="product-img"></div><div class="product-info">
                   <a href="book-detail?id=14" class="product-title">Beartown - Thị Trấn Nhỏ, Giấc Mơ Lớn</a><p class="product-price">279.000đ</p></div></div></div>
               <div class="col"><div class="product-card"><div class="product-img-wrap"><img src="https://firstnews.vn/upload/products/original/-1752812863.jpg" class="product-img"></div><div class="product-info">
                   <a href="book-detail?id=15" class="product-title">Thuyết mặc kệ họ</a><p class="product-price">349.000đ</p></div></div></div>
           </div>
       </div>

       <div class="product-section">
           <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
               <h2 class="section-title">SÁCH BÁN CHẠY</h2>
               <a href="#" class="view-more">Xem thêm <i class="bi bi-chevron-right"></i></a>
           </div>
           <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-4 g-3">
               <div class="col">
                   <div class="product-card">
                       <span class="badge-hot">Hot</span>
                       <div class="product-img-wrap"><img src="https://upload.wikimedia.org/wikipedia/vi/9/9c/Nh%C3%A0_gi%E1%BA%A3_kim_%28s%C3%A1ch%29.jpg" class="product-img"></div>
                       <div class="product-info">
                           <a href="book-detail?id=16" class="product-title">Nhà Giả Kim </a><p class="product-price">199.000đ</p></div>

                   </div>
               </div>
               <div class="col"><div class="product-card"><span class="badge-hot">Hot</span><div class="product-img-wrap"><img src="https://product.hstatic.net/1000376556/product/bia_1_-_ong_gia_va_bien_ca_e1758ab5f74f4f1bb0bd7e48b07611dd_1024x1024.png" class="product-img" alt="Ông già và biển cả"></div><div class="product-info">
                   <a href="book-detail?id=17" class="product-title">Ông già và biển cả</a><p class="product-price">89.000đ</p></div></div></div>
               <div class="col"><div class="product-card"><span class="badge-hot">Hot</span><div class="product-img-wrap"><img src="https://bizweb.dktcdn.net/100/197/269/products/sapiens-luoc-su-ve-loai-nguoi-outline-5-7-2017-02.jpg?v=1520935327270" class="product-img" alt="Sapiens"></div><div class="product-info">
                   <a href="book-detail?id=18" class="product-title">Sapiens - Lược Sử Loài Người</a><p class="product-price">145.000đ</p></div></div></div>
               <div class="col"><div class="product-card"><span class="badge-hot">Hot</span><div class="product-img-wrap"><img src="https://www.nxbtre.com.vn/Images/Book/nxbtre_full_07352019_043547.jpg" class="product-img" alt="Atomic Habits"></div><div class="product-info">
                   <a href="book-detail?id=19" class="product-title">Cà Phê Cùng Tony</a><p class="product-price">189.000đ</p></div></div></div>
           </div>
       </div>

        <div class="product-section all-books-section mt-5">
            <h2 class="section-title mb-4" style="font-size: 1.2rem; font-weight: bold; text-transform: uppercase;">Tất cả sách</h2>

            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">

                <div class="col">
                    <div class="product-card h-100 bg-white rounded-3">
                        <div class="product-img-wrap">
                            <img src="https://cdn1.fahasa.com/media/catalog/product/8/9/8935236430111.jpg" class="product-img" alt="Chí Phèo">
                        </div>
                        <div class="product-info p-3">
                            <div class="category-tag text-success fw-medium small">Văn học</div>
                          <a href="${pageContext.request.contextPath}/book-detail?id=4"
                             class="product-title mt-1">Chí Phèo</a>
                            <div class="author-name">Nam Cao</div>
                            <div class="rating-box">
                                <i class="bi bi-star-fill"></i> 4.5 <span class="text-muted ms-1">(1234)</span>
                            </div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="price-text">299.000đ</span>
                                <button class="btn-add-cart"><i class="bi bi-cart-fill"></i></button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="product-card h-100 bg-white rounded-3">
                        <div class="product-img-wrap">
                            <img src="https://cdn1.fahasa.com/media/catalog/product/8/9/8935280914018.jpg" class="product-img" alt="Murder on the Orient Express">
                        </div>
                        <div class="product-info p-3">
                            <div class="category-tag text-success fw-medium small">Trinh thám</div>
                            <a href="${pageContext.request.contextPath}/book-detail?id=2"
                               class="product-title mt-1">
                                Cuộc Điều Tra Màu Đỏ
                            </a>
                            <div class="author-name">
                                Sir Arthur Conan Doyle</div>
                            <div class="rating-box">
                                <i class="bi bi-star-fill"></i> 4.7 <span class="text-muted ms-1">(987)</span>
                            </div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="price-text">259.000đ</span>
                                <button class="btn-add-cart"><i class="bi bi-cart-fill"></i></button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="product-card h-100 bg-white rounded-3">
                        <div class="product-img-wrap">
                            <img src="https://cdn1.fahasa.com/media/catalog/product/9/7/9786049952074.jpg" class="product-img" alt="Pride and Prejudice">
                        </div>
                        <div class="product-info p-3">
                            <div class="category-tag text-success fw-medium small">Văn học cổ điển</div>
                          <a href="${pageContext.request.contextPath}/book-detail?id=5"
                             class="product-title mt-1">Đồi Gió Hú</a>
                            <div class="author-name">Jane Austen</div>
                            <div class="rating-box">
                                <i class="bi bi-star-fill"></i> 4.8 <span class="text-muted ms-1">(2341)</span>
                            </div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="price-text">279.000đ</span>
                                <button class="btn-add-cart"><i class="bi bi-cart-fill"></i></button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="product-card h-100 border rounded-3 overflow-hidden shadow-sm bg-white">
                        <div class="product-img-wrap" style="height: 380px;">
                            <img src="https://cdn1.fahasa.com/media/catalog/product/i/m/image_234363.jpg" class="w-100 h-100 object-fit-cover" alt="The Great Gatsby">
                        </div>
                        <div class="product-info p-3">
                            <div class="text-success small fw-medium">Văn học</div>
                            <a href="${pageContext.request.contextPath}/book-detail?id=1"
                               class="product-title d-block fw-bold text-dark text-decoration-none mt-1">
                                Gatsby Vĩ Đại
                            </a>
                            <p class="text-muted small mb-1">F. Scott Fitzgerald</p>
                            <div class="rating-box small text-warning mb-3">★ 4.5 <span class="text-muted">(1,234)</span></div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="fw-bold text-success">299.000đ</span>
                                <button class="btn btn-success p-0 d-flex align-items-center justify-content-center" style="width: 32px; height: 32px; border-radius: 6px;">
                                    <i class="bi bi-cart-fill"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="product-card h-100 border rounded-3 overflow-hidden shadow-sm bg-white">
                        <div class="product-img-wrap" style="height: 380px;">
                            <img src="https://www.fahasa.com/blog/wp-content/uploads/2024/11/trang-an.webp" class="w-100 h-100 object-fit-cover" alt="Murder on the Orient Express">
                        </div>
                        <div class="product-info p-3">
                            <div class="text-success small fw-medium">Trinh thám</div>
                           <a href="${pageContext.request.contextPath}/book-detail?id=6"
                              class="product-title d-block fw-bold text-dark text-decoration-none mt-1">
                               Trắng Án
                           </a>
                            <p class="text-muted small mb-1">Agatha Christie</p>
                            <div class="rating-box small text-warning mb-3">★ 4.7 <span class="text-muted">(987)</span></div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="fw-bold text-success">259.000đ</span>
                                <button class="btn btn-success p-0 d-flex align-items-center justify-content-center" style="width: 32px; height: 32px; border-radius: 6px;">
                                    <i class="bi bi-cart-fill"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="product-card h-100 border rounded-3 overflow-hidden shadow-sm bg-white">
                        <div class="product-img-wrap" style="height: 380px;">
                            <img src="https://cdn1.fahasa.com/media/catalog/product/8/9/8935236434393.jpg" class="w-100 h-100 object-fit-cover" alt="Pride and Prejudice">
                        </div>
                        <div class="product-info p-3">
                            <div class="text-success small fw-medium">Văn học cổ điển</div>
                           <a href="${pageContext.request.contextPath}/book-detail?id=7"
                              class="product-title d-block fw-bold text-dark text-decoration-none mt-1">
                               Kiêu Hãnh Và Định Kiến
                           </a>
                            <p class="text-muted small mb-1">Jane Austen</p>
                            <div class="rating-box small text-warning mb-3">★ 4.8 <span class="text-muted">(2,341)</span></div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="fw-bold text-success">279.000đ</span>
                                <button class="btn btn-success p-0 d-flex align-items-center justify-content-center" style="width: 32px; height: 32px; border-radius: 6px;">
                                    <i class="bi bi-cart-fill"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="product-card h-100 border rounded-3 overflow-hidden shadow-sm bg-white">
                        <div class="product-img-wrap" style="height: 380px;">
                            <img src="https://cdn1.fahasa.com/media/catalog/product/9/7/9780345539434.jpg" class="w-100 h-100 object-fit-cover" alt="Brief History of Time">
                        </div>
                        <div class="product-info p-3">
                            <div class="text-success small fw-medium">Khoa học</div>
                           <a href="${pageContext.request.contextPath}/book-detail?id=8"
                              class="product-title d-block fw-bold text-dark text-decoration-none mt-1">
                               Cosmos
                           </a>
                            <p class="text-muted small mb-1">Stephen Hawking</p>
                            <div class="rating-box small text-warning mb-3">★ 4.6 <span class="text-muted">(1,567)</span></div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="fw-bold text-success">500.000đ</span>
                                <button class="btn btn-success p-0 d-flex align-items-center justify-content-center" style="width: 32px; height: 32px; border-radius: 6px;">
                                    <i class="bi bi-cart-fill"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="product-card h-100 border rounded-3 overflow-hidden shadow-sm bg-white">
                        <div class="product-img-wrap" style="height: 380px;">
                            <img src="https://www.netabooks.vn/Data/Sites/1/Product/76511/lich-su-viet-nam-truyen-thong-va-hien-dai.jpg" class="w-100 h-100 object-fit-cover" alt="Sapiens">
                        </div>
                        <div class="product-info p-3">
                            <div class="text-success small fw-medium">Lịch sử</div>
                           <a href="${pageContext.request.contextPath}/book-detail?id=3"
                              class="product-title d-block fw-bold text-dark text-decoration-none mt-1">
                               Lịch Sử Việt Nam -Truyền Thống Và Hiện Đại
                           </a>
                            <p class="text-muted small mb-1">Vũ Minh Giang</p>
                            <div class="rating-box small text-warning mb-3">★ 4.9 <span class="text-muted">(3,456)</span></div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="fw-bold text-success">399.000đ</span>
                                <button class="btn btn-success p-0 d-flex align-items-center justify-content-center" style="width: 32px; height: 32px; border-radius: 6px;">
                                    <i class="bi bi-cart-fill"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="product-card h-100 border rounded-3 overflow-hidden shadow-sm bg-white">
                        <div class="product-img-wrap" style="height: 380px;">
                            <img src="https://cdn1.fahasa.com/media/catalog/product/8/9/8935235238428.jpg" class="w-100 h-100 object-fit-cover" alt="The Notebook">
                        </div>
                        <div class="product-info p-3">
                            <div class="text-success small fw-medium">Lãng mạn</div>
                           <a href="${pageContext.request.contextPath}/book-detail?id=9"
                              class="product-title d-block fw-bold text-dark text-decoration-none mt-1">
                               Romeo Và Juliet
                           </a>
                            <p class="text-muted small mb-1">William Shakespeare</p>
                            <div class="rating-box small text-warning mb-3">★ 4.4 <span class="text-muted">(872)</span></div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="fw-bold text-success">249.000đ</span>
                                <button class="btn btn-success p-0 d-flex align-items-center justify-content-center" style="width: 32px; height: 32px; border-radius: 6px;">
                                    <i class="bi bi-cart-fill"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="product-card h-100 border rounded-3 overflow-hidden shadow-sm bg-white">
                        <div class="product-img-wrap" style="height: 380px;">
                            <img src="https://cdn1.fahasa.com/media/catalog/product/8/9/8934974179665.jpg" class="w-100 h-100 object-fit-cover" alt="Lord of the Rings">
                        </div>
                        <div class="product-info p-3">
                            <div class="text-success small fw-medium">Giả tưởng</div>
                          <a href="${pageContext.request.contextPath}/book-detail?id=10"
                             class="product-title d-block fw-bold text-dark text-decoration-none mt-1">
                              Harry Potter Và Hoàng Tử Lai
                          </a>
                            <p class="text-muted small mb-1">J.K.Rowling</p>
                            <div class="rating-box small text-warning mb-3">★ 4.9 <span class="text-muted">(4,567)</span></div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="fw-bold text-success">599.000đ</span>
                                <button class="btn btn-success p-0 d-flex align-items-center justify-content-center" style="width: 32px; height: 32px; border-radius: 6px;">
                                    <i class="bi bi-cart-fill"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="product-card h-100 border rounded-3 overflow-hidden shadow-sm bg-white">
                        <div class="product-img-wrap" style="height: 380px;">
                            <img src="https://cdn1.fahasa.com/media/catalog/product/8/9/8935086856017.jpg" class="w-100 h-100 object-fit-cover" alt="Thinking Fast and Slow">
                        </div>
                        <div class="product-info p-3">
                            <div class="text-success small fw-medium">Kinh doanh</div>
                         <a href="${pageContext.request.contextPath}/book-detail?id=11"
                            class="product-title d-block fw-bold text-dark text-decoration-none mt-1">
                             Nghĩ Lớn Để Thành Công
                         </a>
                            <p class="text-muted small mb-1">Donald J Trump</p>
                            <div class="rating-box small text-warning mb-3">★ 4.7 <span class="text-muted">(2,198)</span></div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="fw-bold text-success">379.000đ</span>
                                <button class="btn btn-success p-0 d-flex align-items-center justify-content-center" style="width: 32px; height: 32px; border-radius: 6px;">
                                    <i class="bi bi-cart-fill"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>


</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>