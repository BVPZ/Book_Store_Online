package com.bookshop.dao;

import com.bookshop.model.Book;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDao {
    public void  addBook(Book book) throws SQLException
    {

    }
    public static Book getBookById(int id) throws SQLException
    {
        Connection con = DBConnection.getConnection();
        String sql = "select * from books where book_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Book book = new Book();
        book.setId(String.valueOf(id));
        book.setTitle(rs.getString("title"));
        book.setAuthorId(rs.getInt("author_id"));
        book.setPrice(rs.getDouble("price"));
        book.setImageUrl(rs.getString("image"));
        book.setDescription(rs.getString("description"));
        book.setStock(rs.getInt("stock"));
        book.setGenres(id);
        return book;
    }
    public static ArrayList<Book> getNewBooks() throws SQLException
    {
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
        return new_books;
    }
    public static ArrayList<Book> getBestSellerBooks() throws SQLException
    {
        Connection con = DBConnection.getConnection();
        String sql = "select * from books where books.book_id >= 1 limit 4 ";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Book> best_seller_books = new  ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getString("book_id"));
            book.setPrice(rs.getDouble("price"));
            book.setTitle(rs.getString("title"));
            book.setImageUrl(rs.getString("image"));
            best_seller_books.add(book);
        }
        return best_seller_books;
    }
    public static  ArrayList<Book> getAllBooks() throws SQLException
    {
        Connection con = DBConnection.getConnection();
        String sql = "select * from books limit 20 ";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Book> all_books = new  ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getString("book_id"));
            book.setPrice(rs.getDouble("price"));
            book.setTitle(rs.getString("title"));
            book.setImageUrl(rs.getString("image"));
            all_books.add(book);
        }
        return  all_books;
    }
}
