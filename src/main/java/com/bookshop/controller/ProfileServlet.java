package com.bookshop.controller;

import com.bookshop.model.User;
import com.bookshop.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int orderCount = OrderDao.countOrdersByUserId(user.getUserId());
        int booksPurchased = OrderDao.countPurchasedBooksByUserId(user.getUserId());
        
        request.setAttribute("orderCount", orderCount);
        request.setAttribute("booksPurchased", booksPurchased);

        request.getRequestDispatcher("view/profile.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect("home");
    }
}