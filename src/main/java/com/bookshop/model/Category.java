package com.bookshop.model;

import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Category {
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
    public Category() {}
    public Category(int categoryId, String categoryName, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public void setCategoryId(int categoryId) {this.categoryId = categoryId;}
    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public static Category getCategorybyId(int categoryId) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
}
