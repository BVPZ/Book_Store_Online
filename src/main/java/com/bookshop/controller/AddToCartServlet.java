package com.bookshop.controller;

import com.bookshop.dao.CartDao;
import com.bookshop.dao.CartItemDao;
import com.bookshop.model.Book;
import com.bookshop.model.Cart;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            session.setAttribute("error","Cần đăng nhập để thực hiện tính năng này" );
            request.getRequestDispatcher("login").forward(request, response);
            return;
        }
        try{
            Book book =(Book) session.getAttribute("book");
            Cart cart = CartDao.getCartByUserId(user.getUserId());
            CartItemDao.AddItem(cart.getCartId(),book.getBookId());
        }catch (Exception e){
            e.printStackTrace();
        }
        request.getRequestDispatcher("cart").forward(request,response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
