package com.bookshop.controller;

import com.bookshop.dao.CartDao;
import com.bookshop.dao.CartItemDao;
import com.bookshop.model.Cart;
import com.bookshop.model.CartItem;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        if(user==null){
            req.getSession().setAttribute("error","Cần đăng nhập để thực hiện tính năng này");
            resp.sendRedirect("login");
            return;
        }
        try {
            Cart cart = CartDao.getCartByUserId(user.getUserId());
            ArrayList<CartItem> cartItems = CartItemDao.getAllCartItem(cart.getCartId());
            req.setAttribute("cartItems",cartItems);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/view/cart.jsp").forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/cart.jsp").forward(req,resp);
    }
}
