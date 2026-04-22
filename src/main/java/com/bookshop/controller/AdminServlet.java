package com.bookshop.controller;

import com.bookshop.dao.*;
import com.bookshop.model.*;
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
        ArrayList<Book> books = new ArrayList<>();
        try {
            String searchQuery = request.getParameter("searchQuery");
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                //searchBooks
                books = BookDao.searchBooks(searchQuery.trim());
            } else {
                //Lấy tất cả
                books = BookDao.getAllBooks();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("books", books);
        ArrayList<Order> orders = OrderDao.getAllOrders();
        // Doanh thu = tổng tiền số đơn đã hoàn thành
        double totalRevenue = 0;
        for(Order o : orders){
            if("Completed".equalsIgnoreCase(o.getOrderStatus())){
                totalRevenue += o.getTotalPrice();
            }
        }
        request.setAttribute("orders", orders);
        try {
            ArrayList<Author> allAuthors = AuthorDao.getAllAuthors();
            request.setAttribute("allAuthors", allAuthors);

            ArrayList<Category> allGenres = CategoryDao.getAllCategories();
            request.setAttribute("allGenres", allGenres);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 1. Tính các thông số ở đầu trang admin
        request.setAttribute("cardTotalUsers", users.size());
        request.setAttribute("cardTotalBooks", books.size());
        request.setAttribute("cardTotalOrders", orders.size());
        
        // Format lại số
        String revenueStr = "";
        if (totalRevenue >= 1000000) {
            revenueStr = new DecimalFormat("#.##").format(totalRevenue / 1000000) + "M";
        } else if (totalRevenue >= 1000) {
            revenueStr = new DecimalFormat("#.##").format(totalRevenue / 1000) + "K";
        } else {
            revenueStr = String.valueOf(totalRevenue);
        }
        request.setAttribute("cardTotalRevenue", revenueStr);

        // 2. Dữ liệu cho các bảng
        Map<String, Double> revenues = AdminDao.getRevenueLast5Months();
        Map<String, Integer> orderCounts = AdminDao.getOrdersLast5Months();
        Map<String, Integer> categorySales = AdminDao.getBooksCountByCategory();

        // Đổi thành dạng mảng bên để bên js đọc đc
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

        // Đổi thành dạng mảng bên để bên js đọc đc
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

        // Đổi thành dạng mảng bên để bên js đọc đc
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

        String showTab = request.getParameter("showTab");
        if (showTab != null) {
            request.setAttribute("showTab", showTab);
        }

        request.getRequestDispatcher("/view/admin.jsp").forward(request, response);
    }
}
