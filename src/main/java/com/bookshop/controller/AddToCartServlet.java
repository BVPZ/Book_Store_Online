package com.bookshop.controller;

import com.bookshop.dao.CartDao;
import com.bookshop.dao.CartItemDao;
import com.bookshop.model.Book;
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

@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Book book =(Book) session.getAttribute("book");
        if (user == null) {
            ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cartItems");
            CartItem  cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setQuantity(1);
            cartItems.add(cartItem);
            session.setAttribute("cartItems",cartItems);

        }else{
            try{
                Cart cart = CartDao.getCartByUserId(user.getUserId());
                CartItemDao.AddItem(cart.getCartId(),book.getBookId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        session.setAttribute("message","success");
        response.sendRedirect("book-detail?id="+book.getBookId());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
