package com.bookshop.controller;

import com.bookshop.dao.CartDao;
import com.bookshop.dao.UserDao;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/edit-user")
public class EditUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if("update".equals(req.getParameter("action"))){
            int id =Integer.parseInt(req.getParameter("id"));
            String fullName = req.getParameter("fullName");
            String email = req.getParameter("email");
            String number = req.getParameter("phoneNumber");
            String address = req.getParameter("address");
            UserDao.editUser(id,fullName,email,number,address);
            System.out.println("Sua thong tin user "+id+ " thanh cong");
            req.getSession().setAttribute("status","Thay đổi thông tin người dùng thành công");
        }else if ("add".equals(req.getParameter("action"))){
            String username = req.getParameter("userName");
            String password = req.getParameter("password");
            String fullName = req.getParameter("fullName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phoneNumber");
            String address = req.getParameter("address");
            String role = "user";
            if (!UserDao.isUsernameExists(username)){
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPhone(phone);
                user.setAddress(address);
                user.setRole(role);
                boolean rs = UserDao.addUser(user);
                CartDao.addCartByUserName(username);
                if (rs) req.getSession().setAttribute("status","Thêm người dùng thành công");
                else req.getSession().setAttribute("status","Thêm người dùng thất bại");
            }else{
                req.getSession().setAttribute("status","Username đã tồn tại");
            }
        }
        req.getSession().setAttribute("showTab","users");
        resp.sendRedirect("admin");
    }
}
