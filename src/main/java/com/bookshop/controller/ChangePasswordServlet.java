package com.bookshop.controller;

import com.bookshop.dao.UserDao;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (newPassword != null && newPassword.equals(confirmPassword)) {
            boolean success = UserDao.updatePassword(user.getUserId(), oldPassword, newPassword);
            if (success) {
                user.setPassword(newPassword); // Optionally update password in user session object
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/profile?success=password");
            } else {
                response.sendRedirect(request.getContextPath() + "/profile?error=wrong_old_password");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/profile?error=mismatch");
        }
    }
}
