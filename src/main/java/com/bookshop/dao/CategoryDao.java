package com.bookshop.dao;

import com.bookshop.model.Category;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
