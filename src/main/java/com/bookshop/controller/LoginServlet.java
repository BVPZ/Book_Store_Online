package com.bookshop.controller;

import com.bookshop.dao.UserDao;
import com.bookshop.model.User;
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

        try {
            User user = UserDao.login(username, password);

            if (user != null) {
                session.setAttribute("user", user);
                resp.sendRedirect("home");
                return;
            } else {
                // login sai
                session.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
                req.getRequestDispatcher("view/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        req.getRequestDispatcher("view/login.jsp").forward(req, resp);
    }
}