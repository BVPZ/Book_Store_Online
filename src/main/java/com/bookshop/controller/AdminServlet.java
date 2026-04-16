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
        for(Order o : orders){
            System.out.println("ID: " + o.getOrderStatus());
        }
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/view/admin.jsp").forward(request, response);
    }
}
