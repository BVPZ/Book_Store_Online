package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.dao.CartItemDao;
import com.bookshop.model.Book;
import com.bookshop.model.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String idRaw = request.getParameter("id");
        if (idRaw != null && !idRaw.isEmpty()) {
            try {
                int bookId = Integer.parseInt(idRaw);
                Book selectedBook = BookDao.getBookById(bookId);
                CartItem cartItem = new CartItem();
                cartItem.setBook(selectedBook);
                cartItem.setQuantity(1);
                ArrayList<CartItem> cartItemsCheckout = new ArrayList<CartItem>();
                cartItemsCheckout.add(cartItem);
                request.setAttribute("cartItemsCheckout", cartItemsCheckout);
                request.setAttribute("total",Total(cartItemsCheckout));
                request.getRequestDispatcher("/view/checkout.jsp").forward(request, response);
                return;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("home");
            }

        } else {
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] selectedIds = request.getParameterValues("selectedItems");
        for (String id : selectedIds) {
            System.out.println("id: " + id);
        }
        if (selectedIds == null || selectedIds.length == 0) {
            request.getSession().setAttribute("message", "Cần chọn 1 sản phẩm");
            response.sendRedirect("cart");
        }
        ArrayList<CartItem> cartItemsCheckout = new ArrayList<CartItem>();
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            for (String selectedId : selectedIds) {
                selectedId = selectedId.trim();
                String[] parts = selectedId.split(",");
                CartItem cartItem = new CartItem();
                cartItem.setBook(BookDao.getBookById(Integer.parseInt(parts[0])));
                cartItem.setBookId(Integer.parseInt(parts[0]));
                cartItem.setQuantity(Integer.parseInt(parts[1]));
                cartItemsCheckout.add(cartItem);
            }
        }else{
            for (String selectedId : selectedIds) {
                selectedId = selectedId.trim();
                String[] parts = selectedId.split(",");
                cartItemsCheckout.add(CartItemDao.getCartItemByCartItemId(Integer.parseInt(parts[2])));
            }
        }


        request.setAttribute("cartItemsCheckout", cartItemsCheckout);
        request.setAttribute("total",Total(cartItemsCheckout));
        request.getRequestDispatcher("/view/checkout.jsp").forward(request, response);
        return;
    }
    public double Total(ArrayList<CartItem> cartItemsCheckout){
        double total = 0;
        for (CartItem cartItem : cartItemsCheckout) {
            total = total + cartItem.getQuantity()*cartItem.getBook().getPrice();
        }
        return total;
    }
}
