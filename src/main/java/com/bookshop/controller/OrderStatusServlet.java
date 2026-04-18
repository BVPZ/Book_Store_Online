package com.bookshop.controller;

import com.bookshop.dao.CartItemDao;
import com.bookshop.dao.OrderDao;
import com.bookshop.model.CartItem;
import com.bookshop.model.Order;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@WebServlet("/order-status")
public class OrderStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String information = session.getAttribute("information").toString();
        String paymentMethod = session.getAttribute("paymentMethod").toString();
        System.out.println(paymentMethod);

        Double price = Double.parseDouble(session.getAttribute("finalPrice").toString());
        if(session.getAttribute("user")==null){
            // Xử lý khách vãng lai
            ArrayList<CartItem> cartItemsCheckout = (ArrayList<CartItem>) session.getAttribute("cartItemsCheckout");
            ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cartItems");
            Order order = new Order();
            order.setUserId(0);
            order.setOrderStatus("Pending");
            order.setTotalPrice(price);
            order.setInformationDelivery(information);
            OrderDao.insertOrder(order,cartItemsCheckout,paymentMethod);

            Iterator<CartItem> it = cartItems.iterator();
            while(it.hasNext()){
                CartItem item = it.next();

                for(CartItem c : cartItemsCheckout){
                    if(c.getBookId() == item.getBookId()){
                        it.remove();
                        break;
                    }
                }
            }
        }else{
            // Xử lý cho user
            User user = (User)session.getAttribute("user");
            ArrayList<CartItem> cartItemsCheckout = (ArrayList<CartItem>) session.getAttribute("cartItemsCheckout");
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setOrderStatus("Pending");
            order.setTotalPrice(price);
            order.setInformationDelivery(information);
            OrderDao.insertOrder(order,cartItemsCheckout,paymentMethod);
            for (CartItem cartItem : cartItemsCheckout){
                CartItemDao.deleteCartItemByCartItemId(cartItem.getCartItemId());
            }
        }
        request.getRequestDispatcher("/view/order_status.jsp").forward(request,response);
    }
}
