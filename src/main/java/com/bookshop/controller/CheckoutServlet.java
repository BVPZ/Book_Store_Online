package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idRaw = request.getParameter("id");
        
        if (idRaw != null && !idRaw.isEmpty()) {
            try {
                int bookId = Integer.parseInt(idRaw);
                Book selectedBook = BookDao.getBookById(bookId);
                
                if (selectedBook != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("book", selectedBook);
                    request.getRequestDispatcher("/view/checkout.jsp").forward(request, response);
                } else {
                    response.sendRedirect("home");
                }
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
                response.sendRedirect("home");
            }
        } else {
            response.sendRedirect("home");
        }
    }
}