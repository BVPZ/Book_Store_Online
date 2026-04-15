package com.bookshop.controller;

import com.bookshop.dao.CartDao;
import com.bookshop.dao.UserDao;
import com.bookshop.model.User;
import com.bookshop.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        if(!password.equals(confirmPassword)) {
            session.setAttribute("error", "Mật khẩu ko giống nhau");
            response.sendRedirect("register");
            return;
        }
        else{
            if(UserDao.isUsernameExists(username)) {
                session.setAttribute("error", "Usename đã được sử dụng");
                response.sendRedirect("register");
                return;
            }
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setFullName(fullname);
            user.setPhone("");
            user.setAddress("");
            user.setRole("user");
            UserDao.addUser(user);
            CartDao.addCartByUserName(username);
            response.sendRedirect("login");
            session.setAttribute("success", "Đăng ký thành công, bạn có thể đăng nhập");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("view/register.jsp").forward(request, response);
    }
}
