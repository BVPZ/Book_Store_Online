package com.bookshop.controller;

import com.bookshop.dao.OrderDao;
import com.bookshop.model.Order;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/order-history")
public class OrderHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ArrayList<Order> orderList = new ArrayList<>();

        if (user != null) {
            // Lấy từ DB nếu đã login
            orderList = OrderDao.getOrdersByUserId(user.getUserId());
        } else {
            // Lấy từ Cookies nếu là khách vãng lai
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("guest_order_ids".equals(c.getName()) && !c.getValue().isEmpty()) {
                        // Tách chuỗi "1-2-3" thành List
                        String[] idsArray = c.getValue().split("-");
                        List<String> orderIdsList = Arrays.asList(idsArray);

                        orderList = OrderDao.getOrdersByIds(orderIdsList);
                        break;
                    }
                }
            }
        }

        request.setAttribute("orderList", orderList);
        // Lưu ý: Dùng đường dẫn tuyệt đối bắt đầu bằng /view/
        request.getRequestDispatcher("/view/order_history.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}