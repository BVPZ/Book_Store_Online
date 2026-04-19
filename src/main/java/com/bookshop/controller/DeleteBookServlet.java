package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/delete-book")
public class DeleteBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            String idStr = request.getParameter("id");

            if (idStr != null && !idStr.isEmpty()) {
                int bookId = Integer.parseInt(idStr);
                BookDao bookDao = new BookDao();
                bookDao.deleteBook(bookId);

                session.setAttribute("status", "Đã xóa sách thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("status", "Lỗi khi xóa sách: " + e.getMessage());
        }

        session.setAttribute("showTab", "books");
        response.sendRedirect("admin?showTab=books");
    }
}