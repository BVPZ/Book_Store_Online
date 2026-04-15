package com.bookshop.controller;

import com.bookshop.dao.CartDao;
import com.bookshop.dao.CartItemDao;
import com.bookshop.model.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/update-cart-item")
public class UpdateCartItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        int bookId = Integer.parseInt(req.getParameter("bookId"));
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            CartItemDao.updateCartItemQuantity(cartItemId, quantity);
            resp.getWriter().write("OK");
        }else{
            boolean rs=false;
            ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cartItems");
            for (CartItem cartItem : cartItems) {
                if (cartItem.getBookId() == bookId ) {
                    cartItem.setQuantity(quantity);
                    rs = true;
                    break;
                }
            }
            if (rs) System.out.println("CartItem for guest is updated");
            else System.out.println("CartItem for guest isn't updated");
            session.setAttribute("cartItems", cartItems);
        }

    }
}
