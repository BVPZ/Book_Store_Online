package com.bookshop.dao;

import com.bookshop.model.Book;
import com.bookshop.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RecentlyViewedDao {
    public static void addRecentlyViewed(int userId, int bookId) {
        String sql = "INSERT INTO recently_viewed (user_id, book_id) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE viewed_at = CURRENT_TIMESTAMP";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.executeUpdate();

            String deleteSql = "DELETE FROM recently_viewed WHERE user_id = ? AND book_id NOT IN " +
                    "(SELECT book_id FROM (SELECT book_id FROM recently_viewed " +
                    "WHERE user_id = ? ORDER BY viewed_at DESC LIMIT 50) as t)";
            try (PreparedStatement psDel = con.prepareStatement(deleteSql)) {
                psDel.setInt(1, userId);
                psDel.setInt(2, userId);
                psDel.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Book> getRecentlyViewedBooks(int userId) {
        ArrayList<Book> list = new ArrayList<>();
        String sql = "SELECT b.* FROM books b JOIN recently_viewed rv " +
                "ON b.book_id = rv.book_id WHERE rv.user_id = ? " +
                "ORDER BY rv.viewed_at DESC LIMIT 50";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setPrice(rs.getDouble("price"));
                book.setImageUrl(rs.getString("image"));
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void clearAll(int userId) {
        String sql = "DELETE FROM recently_viewed WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}