package com.bookshop.controller;

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

@WebServlet("/order-status")
public class OrderStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String information = session.getAttribute("information").toString();
        Double price = Double.parseDouble(session.getAttribute("finalPrice").toString());
        if(session.getAttribute("user")==null){
            // Xử lý khách vãng lai
            ArrayList<CartItem> cartItemsCheckout = (ArrayList<CartItem>) session.getAttribute("cartItemsCheckout");
            Order order = new Order();
            order.setUserId(0);
            order.setOrderStatus("Pending");
            order.setTotalPrice(price);
            order.setInformationDelivery(information);
            OrderDao.insertOrder(order,cartItemsCheckout);
        }else{
            // Xử lý cho user
            User user = (User)session.getAttribute("user");
            ArrayList<CartItem> cartItemsCheckout = (ArrayList<CartItem>) session.getAttribute("cartItemsCheckout");

            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setOrderStatus("Pending");
            order.setTotalPrice(price);
            order.setInformationDelivery(information);
            OrderDao.insertOrder(order,cartItemsCheckout);
        }
        request.getRequestDispatcher("/view/order_status.jsp").forward(request,response);
    }
}
