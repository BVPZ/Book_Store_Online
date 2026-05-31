package com.bookshop.controller;

import com.bookshop.dao.ReviewDao;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }

        int bookId = Integer.parseInt(req.getParameter("bookId"));
        int rating = Integer.parseInt(req.getParameter("rating"));
        String comment = req.getParameter("comment");

        // Kiểm tra quyền đánh giá
        if (!ReviewDao.canReview(user.getUserId(), bookId)) {
            req.getSession().setAttribute("reviewError", "Bạn cần mua và nhận sách này mới được đánh giá!");
            resp.sendRedirect("book-detail?id=" + bookId);
            return;
        }

        // Kiểm tra đã đánh giá chưa
        if (ReviewDao.hasReviewed(user.getUserId(), bookId)) {
            req.getSession().setAttribute("reviewError", "Bạn đã đánh giá sách này rồi!");
            resp.sendRedirect("book-detail?id=" + bookId);
            return;
        }

        int orderId = ReviewDao.getOrderId(user.getUserId(), bookId);
        boolean success = ReviewDao.addReview(user.getUserId(), bookId, orderId, rating, comment);

        if (success) {
            req.getSession().setAttribute("reviewSuccess", "Cảm ơn bạn đã đánh giá!");
        } else {
            req.getSession().setAttribute("reviewError", "Có lỗi xảy ra, vui lòng thử lại!");
        }

        resp.sendRedirect("book-detail?id=" + bookId);
    }
}