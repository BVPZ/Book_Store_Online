package com.bookshop.controller;

import com.bookshop.dao.CartItemDao;
import com.bookshop.model.CartItem;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/remove-cartitem")
public class RemoveCartItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        String[] parts = request.getParameter("id").split(",");
        if (user != null) {
            CartItemDao.deleteCartItemByCartItemId(Integer.parseInt(parts[0]));
            request.getSession().setAttribute("message","success");
            response.sendRedirect("cart");
        }
        else{
            ArrayList<CartItem> cartItems = (ArrayList<CartItem>)request.getSession().getAttribute("cartItems");
            cartItems.removeIf(item->item.getBookId()==Integer.parseInt(parts[1]));
            request.getSession().setAttribute("cartItems",cartItems);
            request.getSession().setAttribute("message","success");
            response.sendRedirect("cart");
        }
    }
}
