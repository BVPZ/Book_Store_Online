package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.dao.RecentlyViewedDao;
import com.bookshop.model.Book;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/recently-viewed")
public class RecentlyViewedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        ArrayList<Book> recentBooks = new ArrayList<>();

        if (user != null) {
            recentBooks = RecentlyViewedDao.getRecentlyViewedBooks(user.getUserId());
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("recent_viewed".equals(c.getName()) && !c.getValue().isEmpty()) {
                        String[] bookIds = c.getValue().split("-");
                        for (String idStr : bookIds) {
                            try {
                                Book b = BookDao.getBookById(Integer.parseInt(idStr));
                                if (b != null) recentBooks.add(b);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                }
            }
        }

        request.setAttribute("recentBooks", recentBooks);
        request.getRequestDispatcher("/view/recently_viewed.jsp").forward(request, response);
    }
}