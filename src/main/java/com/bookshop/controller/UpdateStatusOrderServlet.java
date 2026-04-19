package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.dao.OrderDao;
import com.bookshop.dao.OrderDetailDao;
import com.bookshop.model.OrderDetail;
import com.bookshop.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet("/update-orderStatus")
public class UpdateStatusOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String newStatus = request.getParameter("status");
        String action = request.getParameter("action");
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); //  bật transaction
            boolean updated = OrderDao.updateStatus(conn, orderId, newStatus);

            if (updated && "Cancelled".equals(newStatus)) {
                ArrayList<OrderDetail> list = OrderDao.getDetailsByOrderId(orderId);
                for (OrderDetail od : list) {
                    BookDao.increaseQuantity(conn, od.getBookId(), od.getQuantity());
                    System.out.println(od.getBookId() + "\t" + od.getQuantity());
                }
            }
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if("adminAction".equals(action)){
            request.getSession().setAttribute("showTab", "orders");
            request.getSession().setAttribute("status", "Cập nhật trạng thái đơn hàng thành công");
            response.sendRedirect("admin");
        }else if("userAction".equals(action)){
            response.sendRedirect("order-history");
        }
    }
}
