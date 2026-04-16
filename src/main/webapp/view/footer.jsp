<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    /* Ép buộc layout từ trong JSP để vượt qua bộ nhớ đệm (cache) của Tomcat/IntelliJ */
    html, body {
        height: auto !important;
        min-height: 100% !important;
        overflow-y: auto !important;
        overflow-x: hidden !important;
    }
    body {
        display: block !important; /* Hủy flex theo chiều dọc gây lỗi chèn footer */
    }
    .main-layout {
        display: flex !important; /* Đảm bảo thanh sidebar bên trái và nội dung vẫn nằm cạnh nhau */
    }
    .site-footer {
        position: relative !important;
        bottom: auto !important;
        width: 100% !important;
        margin-top: 30px !important;
        clear: both !important;
    }
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer_style.css">
<footer class="site-footer">
    <div class="container-fluid px-4">
        <div class="row">
            <!-- Content 1 -->
            <div class="col-lg-3 col-md-6 footer-col">
                <h5 class="footer-heading">Nhà Sách Tri Thức</h5>
                <ul class="footer-contact-list">
                    <li>
                        <i class="bi bi-geo-alt icon-green"></i>
                        <span>HVCN Bưu chính Viễn thông</span>
                    </li>
                    <li>
                        <i class="bi bi-telephone icon-green"></i>
                        <span>0999 999 999</span>
                    </li>
                    <li>
                        <i class="bi bi-envelope icon-green"></i>
                        <span>bvpz@gmail.com</span>
                    </li>
                </ul>
            </div>

            <!-- Content 2 -->
            <div class="col-lg-3 col-md-6 footer-col">
                <h5 class="footer-heading">Điều hướng</h5>
                <ul class="footer-link-list">
                    <li><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
                    <li><a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a></li>
                    <li><a href="${pageContext.request.contextPath}/order-history">Đơn hàng</a></li>
                    <li><a href="${pageContext.request.contextPath}/profile">Tài khoản</a></li>
                    <li><a href="${pageContext.request.contextPath}/recently-viewed">Sách đã xem</a></li>
                </ul>
            </div>

            <!-- Content 3 -->
            <div class="col-lg-3 col-md-6 footer-col">
                <h5 class="footer-heading">Chính sách</h5>
                <ul class="footer-link-list">
                    <li><a href="#">Chính sách bảo mật</a></li>
                    <li><a href="#">Chính sách thanh toán</a></li>
                    <li><a href="#">Chính sách đổi trả</a></li>
                    <li><a href="#">Chính sách vận chuyển</a></li>
                    <li><a href="#">Điều khoản sử dụng</a></li>
                </ul>
            </div>

            <!-- Content 4 -->
            <div class="col-lg-3 col-md-6 footer-col">
                <h5 class="footer-heading">Kết nối với chúng tôi</h5>
                <div class="social-icons">
                    <a href="https://web.facebook.com/dnl.now?locale=vi_VN" target="_blank" class="social-icon">
                        <i class="bi bi-facebook"></i>
                    </a>
                    <a href="#" class="social-icon">
                        <i class="bi bi-tiktok"></i>
                    </a>
                    <a href="#" class="social-icon">
                        <i class="bi bi-instagram"></i>
                    </a>
                    <a href="#" class="social-icon">
                        <i class="bi bi-telegram"></i>
                    </a>
                </div>
                <p class="footer-social-text">
                    Theo dõi chúng tôi để cập nhật tin tức mới nhất về sách và các chương trình khuyến mãi hấp dẫn!
                </p>
            </div>
        </div>

        <div class="footer-bottom">
            <div class="row m-0">
                <div class="col-md-6 text-md-start text-center mb-2 mb-md-0">
                    &copy; 2026 Nhà Sách Tri Thức. All rights reserved.
                </div>
                <div class="col-md-6 text-md-end text-center">
                    Powered by Phúc <span class="heart-icon">❤️</span>
                </div>
            </div>
        </div>
    </div>
</footer>
