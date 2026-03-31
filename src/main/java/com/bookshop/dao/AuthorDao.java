package com.bookshop.dao;

import com.bookshop.model.Author;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDao {
    public static Author getAuthorbyId(int authorId) {
        Author author = new Author();
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select * from authors where author_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,authorId);
            ResultSet rs = ps.executeQuery();

            author.setAuthorId(authorId);
            author.setAuthorName(rs.getString("author_name"));
            author.setBio(rs.getString("bio"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }
}
