package com.bookshop.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.bookshop.dao.BookDao;
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
        
        String bookIdParam = request.getParameter("bookId");
        int bookId = 0;
        Book book = null;

        if (bookIdParam != null && !bookIdParam.isEmpty()) {
            try {
                bookId = Integer.parseInt(bookIdParam);
                book = BookDao.getBookById(bookId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (book == null) {
            book = (Book) session.getAttribute("book");
            if (book != null) {
                bookId = book.getBookId();
            }
        }

        String isAjaxStr = request.getParameter("ajax");
        boolean isAjax = (isAjaxStr != null && isAjaxStr.equals("true")) || 
                         "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        if (book == null) {
            if (isAjax) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\":false,\"message\":\"Không tìm thấy thông tin sách.\"}");
            } else {
                response.sendRedirect("home");
            }
            return;
        }

        if (user == null) {
            ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
                session.setAttribute("cartItems", cartItems);
            }
            
            boolean found = false;
            for (CartItem cartItem : cartItems) {
                if (cartItem.getBookId() == bookId) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                CartItem cartItem = new CartItem();
                cartItem.setBookId(bookId);
                cartItem.setQuantity(1);
                cartItem.setBook(book);
                cartItems.add(cartItem);
            }
            session.setAttribute("cartItems", cartItems);
            System.out.println("CartItems updated, Có " + cartItems.size() + " sản phẩm");
        } else {
            try {
                Cart cart = CartDao.getCartByUserId(user.getUserId());
                if (cart != null && cart.getCartId() > 0) {
                    CartItemDao.AddItem(cart.getCartId(), bookId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        session.setAttribute("message", "success");
        
        if (isAjax) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\":true}");
        } else {
            response.sendRedirect("book-detail?id=" + bookId);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
