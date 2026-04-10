package com.bookshop.controller;

import com.bookshop.dao.RecentlyViewedDao;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/clear-history")
public class ClearHistoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            RecentlyViewedDao.clearAll(user.getUserId());
        } else {
            Cookie resetCookie = new Cookie("recent_viewed", "");
            resetCookie.setMaxAge(0);
            resetCookie.setPath("/");
            response.addCookie(resetCookie);
        }

        response.sendRedirect("recently-viewed");
    }
}