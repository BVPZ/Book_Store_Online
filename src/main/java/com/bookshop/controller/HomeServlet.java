package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.model.Book;
import com.bookshop.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("view/home.jsp").forward(req, resp);
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try{
            HttpSession session = req.getSession();
            session.setAttribute("new_books", BookDao.getNewBooks());
            session.setAttribute("best_seller_books", BookDao.getBestSellerBooks());
            session.setAttribute("all_books", BookDao.getAllBooks());
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("view/home.jsp").forward(req, resp);
    }
}