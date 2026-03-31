package com.bookshop.dao;

import com.bookshop.model.Book;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
