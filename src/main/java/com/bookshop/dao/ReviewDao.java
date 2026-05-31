package com.bookshop.dao;
 
import com.bookshop.model.Review;
import com.bookshop.util.DBConnection;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class ReviewDao {
 
    public static boolean canReview(int userId, int bookId) {
        String sql = "SELECT o.order_id FROM orders o " +
                     "JOIN order_details od ON o.order_id = od.order_id " +
                     "WHERE o.user_id = ? AND od.book_id = ? AND o.status = 'Completed' " +
                     "LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public static boolean hasReviewed(int userId, int bookId) {
        String sql = "SELECT review_id FROM reviews WHERE user_id = ? AND book_id = ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public static int getOrderId(int userId, int bookId) {
        String sql = "SELECT o.order_id FROM orders o " +
                     "JOIN order_details od ON o.order_id = od.order_id " +
                     "WHERE o.user_id = ? AND od.book_id = ? AND o.status = 'Completed' " +
                     "LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("order_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
 
    public static boolean addReview(int userId, int bookId, int orderId, int rating, String comment) {
        String sql = "INSERT INTO reviews (user_id, book_id, order_id, rating, comment) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setInt(3, orderId);
            ps.setInt(4, rating);
            ps.setString(5, comment);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public static List<Review> getReviewsByBookId(int bookId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT r.*, u.full_name, u.username FROM reviews r " +
                     "JOIN users u ON r.user_id = u.user_id " +
                     "WHERE r.book_id = ? ORDER BY r.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Review review = new Review();
                review.setReviewId(rs.getInt("review_id"));
                review.setUserId(rs.getInt("user_id"));
                review.setBookId(rs.getInt("book_id"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setCreatedAt(rs.getTimestamp("created_at"));
                String fullName = rs.getString("full_name");
                review.setReviewerName(fullName != null && !fullName.isEmpty() ? fullName : rs.getString("username"));
                list.add(review);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
 
    public static double getAverageRating(int bookId) {
        String sql = "SELECT AVG(rating) FROM reviews WHERE book_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
 
    public static int getReviewCount(int bookId) {
        String sql = "SELECT COUNT(*) FROM reviews WHERE book_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
 