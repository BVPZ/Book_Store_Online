package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendRedirect("home");
            return;
        }
        try {
            ArrayList<Book> listBooks = BookDao.searchBooks(keyword);
            request.setAttribute("searchResults", listBooks);
            request.setAttribute("searchKeyword", keyword);
            request.getRequestDispatcher("/view/home.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home");
        }
    }
}