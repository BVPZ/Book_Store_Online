package com.bookshop.controller;

import com.bookshop.dao.BookDao;
import com.bookshop.dao.OrderDao;
import com.bookshop.dao.UserDao;
import com.bookshop.model.Book;
import com.bookshop.model.Order;
import com.bookshop.model.OrderDetail;
import com.bookshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.bookshop.dao.AdminDao;
import java.text.DecimalFormat;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        ArrayList<User> users = UserDao.getAllUsers();
        request.setAttribute("users", users);
        ArrayList<Book> books = BookDao.getAllBooks();
        request.setAttribute("books", books);
        ArrayList<Order> orders = OrderDao.getAllOrders();
        double totalRevenue = 0;
        for(Order o : orders){
            if("Completed".equalsIgnoreCase(o.getOrderStatus()) || "Đã giao".equalsIgnoreCase(o.getOrderStatus())){
                totalRevenue += o.getTotalPrice();
            }
        }
        request.setAttribute("orders", orders);
        
        // 1. Calculate basic metrics
        request.setAttribute("cardTotalUsers", users.size());
        request.setAttribute("cardTotalBooks", books.size());
        request.setAttribute("cardTotalOrders", orders.size());
        
        // For revenue, format like 202M
        String revenueStr = "";
        if (totalRevenue >= 1000000) {
            revenueStr = new DecimalFormat("#.##").format(totalRevenue / 1000000) + "M";
        } else if (totalRevenue >= 1000) {
            revenueStr = new DecimalFormat("#.##").format(totalRevenue / 1000) + "K";
        } else {
            revenueStr = String.valueOf(totalRevenue);
        }
        request.setAttribute("cardTotalRevenue", revenueStr);

        // 2. Data for Charts
        Map<String, Double> revenues = AdminDao.getRevenueLast5Months();
        Map<String, Integer> orderCounts = AdminDao.getOrdersLast5Months();
        Map<String, Integer> categorySales = AdminDao.getBooksCountByCategory();

        // Convert Revenues
        StringBuilder revLabels = new StringBuilder("[");
        StringBuilder revValues = new StringBuilder("[");
        int countRev = 0;
        for (Map.Entry<String, Double> entry : revenues.entrySet()) {
            revLabels.append("'").append(entry.getKey()).append("'");
            revValues.append(entry.getValue());
            if (++countRev < revenues.size()) {
                revLabels.append(", ");
                revValues.append(", ");
            }
        }
        revLabels.append("]");
        revValues.append("]");

        // Convert Orders
        StringBuilder orderLabels = new StringBuilder("[");
        StringBuilder orderValues = new StringBuilder("[");
        int countOrder = 0;
        for (Map.Entry<String, Integer> entry : orderCounts.entrySet()) {
            orderLabels.append("'").append(entry.getKey()).append("'");
            orderValues.append(entry.getValue());
            if (++countOrder < orderCounts.size()) {
                orderLabels.append(", ");
                orderValues.append(", ");
            }
        }
        orderLabels.append("]");
        orderValues.append("]");
        
        // Convert Categories
        StringBuilder catLabels = new StringBuilder("[");
        StringBuilder catValues = new StringBuilder("[");
        int countCat = 0;
        for (Map.Entry<String, Integer> entry : categorySales.entrySet()) {
            catLabels.append("'").append(entry.getKey()).append("'");
            catValues.append(entry.getValue());
            if (++countCat < categorySales.size()) {
                catLabels.append(", ");
                catValues.append(", ");
            }
        }
        catLabels.append("]");
        catValues.append("]");

        request.setAttribute("revenueLabels", revLabels.toString());
        request.setAttribute("revenueData", revValues.toString());
        request.setAttribute("orderLabels", orderLabels.toString());
        request.setAttribute("orderData", orderValues.toString());
        request.setAttribute("catLabels", catLabels.toString());
        request.setAttribute("catValues", catValues.toString());

        request.getRequestDispatcher("/view/admin.jsp").forward(request, response);
    }
}
