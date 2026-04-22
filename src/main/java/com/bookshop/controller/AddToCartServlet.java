package com.bookshop.controller;

import java.io.IOException;
import java.util.ArrayList;

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

@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Book book =(Book) session.getAttribute("book");

        if (user == null) {
            ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cartItems");
            if (cartItems.isEmpty()) {
                System.out.println("CartItems is empty");
            }else {
                for (CartItem cartItem : cartItems) {
                    if (cartItem.getBookId() == book.getBookId()) {
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                        session.setAttribute("message", "success");
                        session.setAttribute("cartItems", cartItems);
                        response.sendRedirect("book-detail?id=" + book.getBookId());
                        return;
                    }
                }
            }
            CartItem cartItem = new CartItem();
            cartItem.setBookId(book.getBookId());
            cartItem.setQuantity(1);
            cartItems.add(cartItem);
            session.setAttribute("cartItems",cartItems);
            System.out.println("CartItems added, Có " + cartItems.size() + "sản phẩm");
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
