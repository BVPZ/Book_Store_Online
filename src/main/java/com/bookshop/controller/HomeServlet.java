package com.bookshop.controller;

import com.bookshop.model.Book;
import com.bookshop.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("view/home.jsp").forward(req, resp);
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select * from books where books.book_id >= 10 limit 4 ";
            PreparedStatement ps = con.prepareStatement(sql);
            ArrayList<Book> new_books = new  ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getString("book_id"));
                book.setPrice(rs.getDouble("price"));
                book.setTitle(rs.getString("title"));
                book.setImageUrl(rs.getString("image"));
                new_books.add(book);

            }
            HttpSession session = req.getSession();
            session.setAttribute("new_books", new_books);
            sql = "select * from books where books.book_id >= 1 limit 4 ";
            ps = con.prepareStatement(sql);
            ArrayList<Book> best_seller_books = new  ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getString("book_id"));
                book.setPrice(rs.getDouble("price"));
                book.setTitle(rs.getString("title"));
                book.setImageUrl(rs.getString("image"));
                best_seller_books.add(book);

            }

            session.setAttribute("best_seller_books", best_seller_books);
            sql = "select * from books limit 20 ";
            ps = con.prepareStatement(sql);
            ArrayList<Book> all_books = new  ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getString("book_id"));
                book.setPrice(rs.getDouble("price"));
                book.setTitle(rs.getString("title"));
                book.setImageUrl(rs.getString("image"));
                all_books.add(book);
            }

            session.setAttribute("all_books", all_books);
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("view/home.jsp").forward(req, resp);
    }
}