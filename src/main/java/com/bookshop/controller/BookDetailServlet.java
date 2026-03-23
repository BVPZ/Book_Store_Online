package com.bookshop.controller;

import com.bookshop.model.Book;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/book-detail")
public class BookDetailServlet extends HttpServlet {

    private static final Map<String, Book> bookMap = new HashMap<>();

    static {
        bookMap.put("1", new Book("Gatsby Vĩ Đại", "F. Scott Fitzgerald", "Văn học", 299000,
                "Một kiệt tác văn học Mỹ về cuộc sống xa hoa...",
                "https://cdn1.fahasa.com/media/catalog/product/i/m/image_234363.jpg", 4.5, 1234));

        bookMap.put("2", new Book("Cuộc Điều Tra Màu Đỏ", "Sir Arthur Conan Doyle", "Trinh thám", 259000,
                "Cuộc gặp gỡ định mệnh giữa Sherlock Holmes và bác sĩ Watson...",
                "https://cdn1.fahasa.com/media/catalog/product/8/9/8935280914018.jpg", 4.7, 987));

        bookMap.put("3", new Book("Lịch Sử Việt Nam", "Vũ Minh Giang", "Lịch sử", 399000,
                "Trải dài từ thuở sơ khai đến hiện đại...",
                "https://www.netabooks.vn/Data/Sites/1/Product/76511/lich-su-viet-nam-truyen-thong-va-hien-dai.jpg", 4.9, 3456));

        bookMap.put("4", new Book("Chí Phèo", "Nam Cao", "Văn học", 299000,
                "Tác phẩm hiện thực nổi tiếng của Nam Cao...",
                "https://cdn1.fahasa.com/media/catalog/product/8/9/8935236430111.jpg", 4.5, 1234));

        bookMap.put("5", new Book("Đồi Gió Hú", "Emily Brontë", "Văn học cổ điển", 279000,
                "Câu chuyện tình yêu mãnh liệt và bi kịch...",
                "https://cdn1.fahasa.com/media/catalog/product/9/7/9786049952074.jpg", 4.8, 2341));

        bookMap.put("6", new Book("Trắng Án", "Agatha Christie", "Trinh thám", 259000,
                "Một vụ án bí ẩn với nhiều tình tiết bất ngờ...",
                "https://www.fahasa.com/blog/wp-content/uploads/2024/11/trang-an.webp", 4.7, 987));

        bookMap.put("7", new Book("Kiêu Hãnh Và Định Kiến", "Jane Austen", "Văn học cổ điển", 279000,
                "Một trong những tiểu thuyết nổi tiếng nhất mọi thời đại...",
                "https://cdn1.fahasa.com/media/catalog/product/8/9/8935236434393.jpg", 4.8, 2341));

        bookMap.put("8", new Book("Cosmos", "Stephen Hawking", "Khoa học", 500000,
                "Khám phá vũ trụ và những bí ẩn của khoa học...",
                "https://cdn1.fahasa.com/media/catalog/product/9/7/9780345539434.jpg", 4.6, 1567));

        bookMap.put("9", new Book("Romeo Và Juliet", "William Shakespeare", "Lãng mạn", 249000,
                "Bi kịch tình yêu nổi tiếng nhất thế giới...",
                "https://cdn1.fahasa.com/media/catalog/product/8/9/8935235238428.jpg", 4.4, 872));

        bookMap.put("10", new Book("Harry Potter Và Hoàng Tử Lai", "J.K. Rowling", "Giả tưởng", 599000,
                "Phần 6 trong series Harry Potter...",
                "https://cdn1.fahasa.com/media/catalog/product/8/9/8934974179665.jpg", 4.9, 4567));

        bookMap.put("11", new Book("Nghĩ Lớn Để Thành Công", "Donald J Trump", "Kinh doanh", 379000,
                "Tư duy lớn để đạt thành công...",
                "https://cdn1.fahasa.com/media/catalog/product/8/9/8935086856017.jpg", 4.7, 2198));


        bookMap.put("12", new Book("The Correspondent", "Nhiều tác giả", "Ngoại văn", 299000,
                "Tác phẩm văn học nổi bật...",
                "https://m.media-amazon.com/images/I/81PndgdGCXL._AC_UF1000,1000_QL80_.jpg", 4.3, 150));

        bookMap.put("13", new Book("Ngày Nay Có Một Ông Trời", "Nhiều tác giả", "Văn học", 259000,
                "Câu chuyện suy ngẫm về cuộc sống...",
                "https://bizweb.dktcdn.net/thumb/1024x1024/100/576/749/products/38f61786-16ea-4a59-8f90-a2b5dd8978c4.jpg?v=1773741971217", 4.5, 320));

        bookMap.put("14", new Book("Beartown - Thị Trấn Nhỏ, Giấc Mơ Lớn", "Fredrik Backman", "Tiểu thuyết", 279000,
                "Một câu chuyện cảm động về cộng đồng...",
                "https://cdn1.fahasa.com/media/catalog/product/z/7/z7012700093197_a8fafde4c12b2c28a893345d31d1beb2.jpg", 4.8, 850));

        bookMap.put("15", new Book("Thuyết mặc kệ họ", "Nhiều tác giả", "Kỹ năng sống", 349000,
                "Cách giữ tinh thần lạc quan và phớt lờ định kiến...",
                "https://firstnews.vn/upload/products/original/-1752812863.jpg", 4.4, 432));

        // THÊM SÁCH TỪ HTML GIAO DIỆN BÁN CHẠY
        bookMap.put("16", new Book("Nhà Giả Kim", "Paulo Coelho", "Văn học", 199000,
                "Chuyến hành trình đi tìm vận mệnh của cậu bé chăn cừu...",
                "https://upload.wikimedia.org/wikipedia/vi/9/9c/Nh%C3%A0_gi%E1%BA%A3_kim_%28s%C3%A1ch%29.jpg", 4.9, 12000));

        bookMap.put("17", new Book("Ông già và biển cả", "Ernest Hemingway", "Văn học cổ điển", 89000,
                "Cuộc chiến kiên cường của ông lão đánh cá...",
                "https://product.hstatic.net/1000376556/product/bia_1_-_ong_gia_va_bien_ca_e1758ab5f74f4f1bb0bd7e48b07611dd_1024x1024.png", 4.8, 5400));

        bookMap.put("18", new Book("Sapiens - Lược Sử Loài Người", "Yuval Noah Harari", "Lịch sử", 145000,
                "Cuốn sách bao quát về lịch sử tiến hóa của nhân loại...",
                "https://bizweb.dktcdn.net/100/197/269/products/sapiens-luoc-su-ve-loai-nguoi-outline-5-7-2017-02.jpg?v=1520935327270", 4.9, 8700));

        bookMap.put("19", new Book("Cà Phê Cùng Tony", "Tony Buổi Sáng", "Kỹ năng sống", 189000,
                "Những bài học cuộc sống hài hước, thực tế...",
                "https://www.nxbtre.com.vn/Images/Book/nxbtre_full_07352019_043547.jpg", 4.7, 9200));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookId = request.getParameter("id");
        Book book = bookMap.get(bookId);

        if (book != null) {
            request.setAttribute("book", book);
            request.getRequestDispatcher("/view/book_detail.jsp").forward(request, response);
        } else {
            response.getWriter().println("<h1>Khong tim thay sach nay!</h1>");
        }
    }
}