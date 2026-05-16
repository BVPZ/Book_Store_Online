package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.model.Book;
import com.bookshop.model.CartItem;
import com.bookshop.service.AiRecommendService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            session.setAttribute("new_books", BookDao.getNewBooks());
            session.setAttribute("best_seller_books", BookDao.getBestSellerBooks());
            req.setAttribute("all_books", BookDao.getAllBooks());

            List<Book> viewedBooks = (List<Book>) session.getAttribute("viewedBookHistory");

            if (viewedBooks != null && !viewedBooks.isEmpty()) {
                ArrayList<Book> allBooks = BookDao.getAllBooks();
                session.setAttribute("suggested_books",
                        AiRecommendService.getAiSuggestions(viewedBooks, allBooks));
            } else {
                session.setAttribute("suggested_books",
                        BookDao.getSuggestedBooks(0, 0, 0));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (session.getAttribute("cartItems") == null) {
            session.setAttribute("cartItems", new ArrayList<CartItem>());
        }

        req.getRequestDispatcher("/view/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}