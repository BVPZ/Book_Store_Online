package com.bookshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bookshop.model.Category;
import com.bookshop.util.DBConnection;

public class CategoryDao {
    public static Category getCategoryById(int categoryId) {
        Category category = new Category();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "select * from categories where category_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            category.setCategoryId(categoryId);
            category.setCategoryName(rs.getString("category_name"));
            category.setCategoryDescription(rs.getString("description"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    public static Integer getCategoryIdByName(String categoryName) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT category_id FROM categories WHERE LOWER(category_name) = ? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, categoryName.toLowerCase().trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("category_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Category> getAllCategories() {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                list.add(category);
            }
            System.out.println("Lấy danh sách Thể loại thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách Thể loại: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static boolean addCategory(String categoryName) {
        String sql = "INSERT INTO categories (category_name) VALUES (?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, categoryName);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm Thể loại: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Thực thi SQL tùy ý để lấy danh sách thể loại
    public static ArrayList<Category> getCategoriesFromSql(String sql) throws SQLException {
        ArrayList<Category> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            java.sql.ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            while (rs.next()) {
                Category category = new Category();
                for (int i = 1; i <= colCount; i++) {
                    String col = meta.getColumnLabel(i).toLowerCase();
                    switch (col) {
                        case "category_id": category.setCategoryId(rs.getInt(i)); break;
                        case "category_name": category.setCategoryName(rs.getString(i)); break;
                        case "description": category.setCategoryDescription(rs.getString(i)); break;
                        // Có thể mở rộng thêm các trường khác nếu cần
                    }
                }
                list.add(category);
            }
        }
        return list;
    }

}
