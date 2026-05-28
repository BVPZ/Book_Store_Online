package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.dao.CategoryDao;
import com.bookshop.model.Book;
import com.bookshop.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy cid từ link (ví dụ: category?cid=3)
        String cidStr = request.getParameter("cid");

        if (cidStr != null) {
            try {
                int categoryId = Integer.parseInt(cidStr);

                // 2. Gọi hàm lấy sách từ DAO
                ArrayList<Book> list = BookDao.getBooksByCategoryId(categoryId);
                Category category = CategoryDao.getCategoryById(categoryId);

                // 3. Đẩy list sách vào request
                // Lưu ý quan trọng: Ở file home.jsp bạn đang dùng ${sessionScope.all_books}
                // Nhưng khi lọc, ta dùng requestScope để dữ liệu mới đè lên dữ liệu cũ
                request.setAttribute("all_books", list);
                request.setAttribute("selectedCategory", category);
                request.getRequestDispatcher("/view/home.jsp").forward(request, response);

            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("home"); // Nếu lỗi thì quay về trang chủ
            }
        } else {
            response.sendRedirect("home");
        }
    }
}