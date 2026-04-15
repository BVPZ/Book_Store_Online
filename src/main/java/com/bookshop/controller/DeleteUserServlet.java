package com.bookshop.controller;

import com.bookshop.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        if(UserDao.deleteUser(userId)){
            req.getSession().setAttribute("status","Xóa người dùng thành công");
        }else{
            req.getSession().setAttribute("status","Xóa người dùng thất bại");
        }
        req.getSession().setAttribute("showTab","user");
        resp.sendRedirect("admin");
    }
}
