<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Nhà Sách Tri Thức - Trang Chủ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        :root {
            --primary-green: #00a651;
            --hover-green: #e6f6ec;
            --price-green: #00b359;
            --bg-body: #f5f5fa;
        }

        /* --- PHẦN CỐ ĐỊNH KHUNG NGOÀI --- */
        html, body {
            height: 100%;
            margin: 0;
            overflow: hidden; /* Cấm cuộn toàn trang */
        }

        body {
            display: flex;
            flex-direction: column; /* Header -> Nav -> Main xếp dọc */
            background-color: var(--bg-body);
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
        }

        .top-header {
            flex-shrink: 0; /* Giữ nguyên chiều cao header */
            background-color: #fff;
            padding: 15px 0;
            border-bottom: 1px solid #eee;
            z-index: 1020;
        }

        .main-nav {
            flex-shrink: 0; /* Giữ nguyên chiều cao navbar */
            background-color: #fff;
            border-bottom: 1px solid #e0e0e0;
            font-size: 14px;
            z-index: 1010;
        }

        /* Container bao quanh Sidebar và Content */
        main.main-layout {
            flex-grow: 1; /* Chiếm trọn không gian còn lại của màn hình */
            display: flex;
            overflow: hidden; /* Ngăn main cuộn */
        }

        /* Sidebar đứng yên bên trái */
        .sidebar-wrapper {
            width: 16.66667%; /* Tương đương col-lg-2 */
            height: 100%;
            overflow-y: auto; /* Nếu menu quá dài vẫn có thể cuộn riêng bên trong sidebar */
            background: transparent;
            padding: 24px 0 24px 24px;
            flex-shrink: 0;
        }

        /* VÙNG NỘI DUNG CHÍNH (PHẦN DUY NHẤT ĐƯỢC CUỘN) */
        .content-wrapper {
            flex-grow: 1;
            height: 100%;
            overflow-y: auto; /* Kích hoạt cuộn ở đây */
            padding: 24px;
        }

        /* --- GIỮ NGUYÊN UI HIỆN TẠI CỦA BẠN --- */
        .logo-text { color: var(--primary-green); font-weight: bold; font-size: 20px; line-height: 1; }
        .logo-sub { font-size: 11px; color: #666; }
        .search-btn { background-color: var(--primary-green); color: white; padding: 0 20px; border: none; }
        .search-btn:hover { background-color: #008a43; }
        .icon-btn { text-align: center; color: #555; text-decoration: none; font-size: 13px; margin-left: 20px;}
        .icon-btn i { font-size: 20px; display: block; margin-bottom: 2px;}
        .icon-btn:hover { color: var(--primary-green); }

        .nav-link-custom { color: #333; text-decoration: none; padding: 10px 15px; display: inline-block;}
        .nav-link-custom:hover { color: var(--primary-green); }
        .nav-promo { color: var(--primary-green); font-size: 13px; font-weight: 500;}
        .nav-promo.orange { color: #ff6a00; }

        .sidebar-menu { background: #fff; border-radius: 8px; padding: 10px 0; box-shadow: 0 1px 2px rgba(0,0,0,0.05); }
        .menu-item { padding: 12px 20px; color: #333; text-decoration: none; display: block; font-size: 14px; transition: 0.2s;}
        .menu-item:hover, .menu-item.active { background-color: var(--hover-green); color: var(--primary-green); font-weight: 500;}
        .promo-banner { background: linear-gradient(135deg, #ff8a00, #ff4c00); border-radius: 8px; padding: 20px; color: white; margin-top: 15px; }

        .section-title { font-size: 18px; font-weight: 600; text-transform: uppercase; color: #333; margin-bottom: 0;}
        .view-more { font-size: 13px; color: var(--primary-green); text-decoration: none; float: right; margin-top: 4px;}
        .product-section { background: #fff; padding: 20px; border-radius: 8px; margin-bottom: 20px; box-shadow: 0 1px 2px rgba(0,0,0,0.05);}

        .product-card { border: 1px solid #f0f0f0; border-radius: 8px; overflow: hidden; position: relative; transition: 0.3s; height: 100%; display: flex; flex-direction: column; background: #fff;}
        .product-card:hover { box-shadow: 0 4px 12px rgba(0,0,0,0.1); transform: translateY(-3px);}
        .product-img-wrap { padding: 15px; text-align: center; background: #f9f9f9;}
        .product-img { max-width: 100%; height: 200px; object-fit: contain; }
        .product-info { padding: 15px; flex-grow: 1; display: flex; flex-direction: column; justify-content: space-between;}
        .product-title { font-size: 14px; color: #333; text-decoration: none; display: block; margin-bottom: 8px; line-height: 1.4; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;}
        .product-price { color: var(--price-green); font-size: 16px; font-weight: bold; margin: 0;}
        .badge-hot { position: absolute; top: 10px; right: 10px; background: #dc3545; color: white; font-size: 11px; padding: 3px 8px; border-radius: 4px; font-weight: bold;}

        /* Làm đẹp thanh cuộn */
        .content-wrapper::-webkit-scrollbar { width: 6px; }
        .content-wrapper::-webkit-scrollbar-thumb { background: #ccc; border-radius: 10px; }

        /* CSS cho mục Tất cả sách */
        .all-books-section .product-card {
            transition: all 0.3s ease;
            border: 1px solid #eee !important;
        }

        .all-books-section .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.08) !important;
        }

        .all-books-section .product-img-wrap {
            height: 380px; /* Chiều cao cố định cho ảnh giống mẫu */
            overflow: hidden;
        }

        .all-books-section .product-img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .all-books-section .product-title {
            font-size: 0.95rem;
            font-weight: 700;
            color: #333;
            text-decoration: none;
            display: -webkit-box;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .all-books-section .author-name {
            font-size: 0.8rem;
            color: #777;
            margin-bottom: 4px;
        }

        .all-books-section .rating-box {
            font-size: 0.8rem;
            color: #ffc107;
            margin-bottom: 12px;
        }

        .all-books-section .price-text {
            font-size: 1.1rem;
            font-weight: 700;
            color: #198754;
        }

        .all-books-section .btn-add-cart {
            width: 35px;
            height: 35px;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 6px;
            background-color: #198754;
            border: none;
            color: white;
        }

        /* Import icon nếu bạn chưa có */
        @import url("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css");
    </style>
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
                <a href="login" class="icon-btn"><i class="bi bi-person"></i>Tài khoản</a>
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
                            <a href="#" class="product-title">The Correspondent </a>
                            <p class="product-price">299.000đ</p>
                        </div>
                    </div>
                </div>
                <div class="col"><div class="product-card"><div class="product-img-wrap"><img src="https://bizweb.dktcdn.net/thumb/1024x1024/100/576/749/products/38f61786-16ea-4a59-8f90-a2b5dd8978c4.jpg?v=1773741971217" class="product-img"></div><div class="product-info"><a href="#" class="product-title">Ngày Nay Có Một Ông Trời</a><p class="product-price">259.000đ</p></div></div></div>
                <div class="col"><div class="product-card"><div class="product-img-wrap"><img src="https://cdn1.fahasa.com/media/catalog/product/z/7/z7012700093197_a8fafde4c12b2c28a893345d31d1beb2.jpg" class="product-img"></div><div class="product-info"><a href="#" class="product-title">Beartown - Thị Trấn Nhỏ, Giấc Mơ Lớn</a><p class="product-price">279.000đ</p></div></div></div>
                <div class="col"><div class="product-card"><div class="product-img-wrap"><img src="https://firstnews.vn/upload/products/original/-1752812863.jpg" class="product-img"></div><div class="product-info"><a href="#" class="product-title">Thuyết mặc kệ họ</a><p class="product-price">349.000đ</p></div></div></div>
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
                        <div class="product-info"><a href="#" class="product-title">Nhà Giả Kim </a><p class="product-price">199.000đ</p></div>

                    </div>
                </div>
                <div class="col"><div class="product-card"><span class="badge-hot">Hot</span><div class="product-img-wrap"><img src="https://product.hstatic.net/1000376556/product/bia_1_-_ong_gia_va_bien_ca_e1758ab5f74f4f1bb0bd7e48b07611dd_1024x1024.png" class="product-img" alt="Ông già và biển cả"></div><div class="product-info"><a href="#" class="product-title">Ông già và biển cả</a><p class="product-price">89.000đ</p></div></div></div>
                <div class="col"><div class="product-card"><span class="badge-hot">Hot</span><div class="product-img-wrap"><img src="https://bizweb.dktcdn.net/100/197/269/products/sapiens-luoc-su-ve-loai-nguoi-outline-5-7-2017-02.jpg?v=1520935327270" class="product-img" alt="Sapiens"></div><div class="product-info"><a href="#" class="product-title">Sapiens - Lược Sử Loài Người</a><p class="product-price">145.000đ</p></div></div></div>
                <div class="col"><div class="product-card"><span class="badge-hot">Hot</span><div class="product-img-wrap"><img src="https://www.nxbtre.com.vn/Images/Book/nxbtre_full_07352019_043547.jpg" class="product-img" alt="Atomic Habits"></div><div class="product-info"><a href="#" class="product-title">Cà Phê Cùng Tony</a><p class="product-price">189.000đ</p></div></div></div>

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
                            <a href="#" class="product-title mt-1">Chí Phèo</a>
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
                            <a href="#" class="product-title mt-1">Cuộc Điều Tra Màu Đỏ</a>
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
                            <a href="#" class="product-title mt-1">Đồi Gió Hú</a>
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
                            <a href="#" class="product-title d-block fw-bold text-dark text-decoration-none mt-1">Gatsby Vĩ Đại</a>
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
                            <a href="#" class="product-title d-block fw-bold text-dark text-decoration-none mt-1">Trắng Án</a>
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
                            <a href="#" class="product-title d-block fw-bold text-dark text-decoration-none mt-1">Kiêu Hãnh Và Định Kiến</a>
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
                            <a href="#" class="product-title d-block fw-bold text-dark text-decoration-none mt-1">Cosmos</a>
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
                            <a href="#" class="product-title d-block fw-bold text-dark text-decoration-none mt-1">Lịch Sử Việt Nam -Truyền Thống Và Hiện Đại</a>
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
                            <a href="#" class="product-title d-block fw-bold text-dark text-decoration-none mt-1">Romeo Và Juliet</a>
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
                            <a href="#" class="product-title d-block fw-bold text-dark text-decoration-none mt-1">Harry Potter Và Hoàng Tử Lai</a>
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
                            <a href="#" class="product-title d-block fw-bold text-dark text-decoration-none mt-1">Nghĩ Lớn Để Thành Công</a>
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