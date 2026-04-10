package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.dao.RecentlyViewedDao;
import com.bookshop.model.Book;
import com.bookshop.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

@WebServlet("/book-detail")
public class BookDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookId = Integer.parseInt(request.getParameter("id"));

        try {
            Book book = BookDao.getBookById(bookId);
            if (book != null) {
                saveRecentlyViewed(request, response, bookId);

                request.setAttribute("book", book);
                request.getSession().setAttribute("book", book);
                request.getRequestDispatcher("/view/book_detail.jsp").forward(request, response);
            } else {
                response.getWriter().println("<h1>Khong tim thay sach nay!</h1>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveRecentlyViewed(HttpServletRequest request, HttpServletResponse response, int bookId) {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            RecentlyViewedDao.addRecentlyViewed(user.getUserId(), bookId);
        } else {
            String recentStr = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("recent_viewed".equals(c.getName())) {
                        recentStr = c.getValue();
                        break;
                    }
                }
            }

            LinkedList<String> list = new LinkedList<>(Arrays.asList(recentStr.split("-")));
            list.removeIf(String::isEmpty);
            list.remove(String.valueOf(bookId));
            list.addFirst(String.valueOf(bookId));

            while (list.size() > 50) {
                list.removeLast();
            }

            Cookie recentCookie = new Cookie("recent_viewed", String.join("-", list));
            recentCookie.setMaxAge(60 * 60 * 24 * 30);
            recentCookie.setPath("/");
            response.addCookie(recentCookie);
        }
    }
}