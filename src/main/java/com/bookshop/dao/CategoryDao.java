package com.bookshop.dao;

import com.bookshop.model.Category;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
