package com.bookshop.controller;

import com.mysql.cj.Session;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && "admin".equals(password)) {
            // login đúng
            session.setAttribute("user", username);
            resp.sendRedirect("home");
        } else {
            // login sai
            session.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
            req.getRequestDispatcher("view/login.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        req.getRequestDispatcher("view/login.jsp").forward(req, resp);
    }
}