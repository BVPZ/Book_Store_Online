package com.bookshop.controller;

import com.bookshop.dao.OrderDao;
import com.bookshop.dao.PaymentDao;
import com.bookshop.model.Order;
import com.bookshop.model.OrderDetail;
import com.bookshop.model.Payment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/order-detail")
public class OrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        Order order = OrderDao.getOrderById(orderId);
        ArrayList<OrderDetail> orderDetails = OrderDao.getDetailsByOrderId(orderId);
        Payment payment = PaymentDao.getPaymentByOrderId(orderId);
        req.setAttribute("orderDetails", orderDetails);
        req.setAttribute("payment", payment);
        req.setAttribute("order", order);
        req.getRequestDispatcher("/view/order-detail.jsp").forward(req, resp);
    }
}
