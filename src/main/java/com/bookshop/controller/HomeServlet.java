package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();

            // 1. Những danh sách cố định (Sách mới, Bán chạy) thì giữ trong Session
            // để Server không phải load lại nhiều lần, giúp web chạy nhanh hơn.
            if (session.getAttribute("new_books") == null) {
                session.setAttribute("new_books", BookDao.getNewBooks());
            }
            if (session.getAttribute("best_seller_books") == null) {
                session.setAttribute("best_seller_books", BookDao.getBestSellerBooks());
            }

            // 2. TẤT CẢ SÁCH: Chuyển sang dùng request.setAttribute
            // Lý do: Để khi nguoi dep nhấn vào "Tất cả sách", nó luôn load lại danh sách mới nhất
            // và quan trọng là nó cùng cấp độ (Scope) với CategoryServlet, giúp hiển thị chuẩn xác.
            req.setAttribute("all_books", BookDao.getAllBooks());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. Forward về giao diện (Đảm bảo có dấu / ở đầu đường dẫn)
        req.getRequestDispatcher("/view/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Gọi lại doGet để dù người dùng dùng phương thức nào thì trang vẫn load đúng
        doGet(req, resp);
    }
}