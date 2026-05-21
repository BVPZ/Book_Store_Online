package com.bookshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bookshop.model.Author;
import com.bookshop.util.DBConnection;

public class AuthorDao {
    public static Author getAuthorbyId(int authorId) {
        Author author = new Author();
        try(Connection con = DBConnection.getConnection()){

            String sql = "select * from authors where author_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,authorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                author.setAuthorId(authorId);
                author.setAuthorName(rs.getString("author_name"));
                author.setBio(rs.getString("bio"));
                System.out.println("lay thong tin tac gia thanh cong");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return author;
    }

    public static ArrayList<Author> getAllAuthors() {
        ArrayList<Author> list = new ArrayList<>();
        String sql = "SELECT * FROM authors";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt("author_id"));
                author.setAuthorName(rs.getString("author_name"));
                list.add(author);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static boolean addAuthor(String authorName) {
        String sql = "INSERT INTO authors (author_name) VALUES (?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, authorName);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm tác giả: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static int getAuthorIdByName(String authorName) {
        String sql = "SELECT author_id FROM authors WHERE author_name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, authorName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("author_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Thực thi SQL tùy ý để lấy danh sách tác giả
    public static ArrayList<Author> getAuthorsFromSql(String sql) throws SQLException {
        ArrayList<Author> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            java.sql.ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            while (rs.next()) {
                Author author = new Author();
                for (int i = 1; i <= colCount; i++) {
                    String col = meta.getColumnLabel(i).toLowerCase();
                    switch (col) {
                        case "author_id": author.setAuthorId(rs.getInt(i)); break;
                        case "author_name": author.setAuthorName(rs.getString(i)); break;
                        case "bio": author.setBio(rs.getString(i)); break;
                        // Có thể mở rộng thêm các trường khác nếu cần
                    }
                }
                list.add(author);
            }
        }
        return list;
    }
}
