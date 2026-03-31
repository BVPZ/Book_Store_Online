package com.bookshop.model;

import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Author {
    private int authorId;
    private String authorName;
    private String bio;
    public Author() {}
    public Author(int authorId, String authorName, String bio) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.bio = bio;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
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
