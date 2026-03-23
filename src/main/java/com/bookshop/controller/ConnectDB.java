package com.bookshop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bookshop.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
@WebServlet("/connectDB")
public class ConnectDB extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            IOException, ServletException{
        Connection conn = DBConnection.getConnection();
        if (conn != null){
            req.getRequestDispatcher("view/success.jsp").forward(req, resp);
        }else{
            req.getRequestDispatcher("view/fail.jsp").forward(req, resp);
        }
    }
}
