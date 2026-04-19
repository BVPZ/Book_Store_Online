package com.bookshop.controller;

import com.bookshop.dao.AuthorDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/save-author")
public class SaveAuthorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String authorName = request.getParameter("authorName");
        if (authorName != null && !authorName.trim().isEmpty()) {
            AuthorDao.addAuthor(authorName.trim());
        }
        response.sendRedirect("admin?showTab=books");
    }
}
