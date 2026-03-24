package com.bookshop.model;


import java.util.UUID;

public class Book {
    private String id;
    private String title;
    private String author;
    private String genre;
    private double price;
    private String description;
    private String imageUrl;
    private double rating;
    private int reviewCount;
    public Book() {
        this.id = UUID.randomUUID().toString();
    }

    public Book(String title, String author, String genre, double price, String description, String imageUrl, double rating, int reviewCount) {
        this();
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

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