package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.model.Book;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/book-detail")
public class BookDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookId = Integer.parseInt(request.getParameter("id")) ;
        System.out.println("bookId:"+bookId);
        Book book = null;
        try {
            book = BookDao.getBookById(bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (book != null) {
            request.setAttribute("book" , book);
            request.getSession().setAttribute("book",book);
            request.getRequestDispatcher("/view/book_detail.jsp").forward(request, response);
        } else {
            response.getWriter().println("<h1>Khong tim thay sach nay!</h1>");
        }
    }
}