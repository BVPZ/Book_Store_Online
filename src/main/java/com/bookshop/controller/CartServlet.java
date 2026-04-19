package com.bookshop.controller;

import com.bookshop.dao.BookDao;
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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        if(user==null){
            if (session.getAttribute("cartItems")==null){
                ArrayList<CartItem> cartItems = new ArrayList<>();
                session.setAttribute("cartItems",cartItems);
            }else{
                ArrayList<CartItem> cartItems = (ArrayList<CartItem>)session.getAttribute("cartItems");
                ArrayList<CartItem> cartItems2 = new ArrayList<>();
                for (CartItem cartItem : cartItems) {
                    CartItem cartItem2 = cartItem;
                    cartItem2.setBook(BookDao.getBookById(cartItem.getBookId()));
                    cartItems2.add(cartItem2);
                }
                session.setAttribute("cartItems",cartItems2);
            }
        }else {
            try {
                Cart cart = CartDao.getCartByUserId(user.getUserId());
                ArrayList<CartItem> cartItems = CartItemDao.getAllCartItem(cart.getCartId());
                req.setAttribute("cartItems",cartItems);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        req.getRequestDispatcher("/view/cart.jsp").forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/cart.jsp").forward(req,resp);
    }
}
