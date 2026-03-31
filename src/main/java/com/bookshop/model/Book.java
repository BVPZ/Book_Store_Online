package com.bookshop.model;


import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Book {
    private String id;
    private String title;
    private int authorId;
    private ArrayList<String> genres;
    private double price;
    private int stock;
    private String description;
    private String imageUrl;
    private double rating;
    private int reviewCount;
    public Book() {
        this.id = UUID.randomUUID().toString();
    }

    public Book(String title, int authorId, ArrayList<String> genre, double price, String description, String imageUrl, double rating, int reviewCount) {
        this();
        this.title = title;
        this.authorId = authorId;
        this.genres = genre;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public ArrayList<String> getGenres() { return genres; }
    public void setGenres(int bookId) {
        ArrayList<String> genres = new ArrayList<>();
        try(Connection con = DBConnection.getConnection()) {
            String sql = "select * from book_categories where book_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                genres.add(Category.getCategorybyId(rs.getInt("category_id")).getCategoryName());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        this.genres = genres;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }

    public String getFormattedPrice() {
        java.text.NumberFormat formatter = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
        return formatter.format(this.price).replace("₫", "đ");
    }
}