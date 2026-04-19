package com.bookshop.model;


import com.bookshop.dao.AuthorDao;
import com.bookshop.dao.BookCategoryDao;
import java.util.*;

public class Book {
    private int bookId;
    private String title;
    private int authorId;
    private ArrayList<String> genres;
    private double price;
    private int stock;
    private String description;
    private String image;
    private double rating;
    private int reviewCount;
    private String authorName;



    public Book() {

    }

    public Book(String title, int authorId, ArrayList<String> genre, double price, String description, String image, double rating, int reviewCount) {
        this();
        this.title = title;
        this.authorId = authorId;
        this.genres = genre;
        this.price = price;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    public int getBookId() { return bookId; }

    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public ArrayList<String> getGenres() { return genres; }
    public void setGenres() {this.genres = BookCategoryDao.getAllGenresByBookId(this.bookId);}

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return image; }
    public void setImageUrl(String imageUrl) { this.image = imageUrl; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }

    public String getFormattedPrice() {
        java.text.NumberFormat formatter = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
        return formatter.format(this.price).replace("₫", "đ");
    }

    public String getAuthorName() {
        if (this.authorName != null && !this.authorName.isEmpty()) {
            return this.authorName;
        }
        try {
            return AuthorDao.getAuthorbyId(this.authorId).getAuthorName();
        } catch (Exception e) {
            return "Chưa có tác giả";
        }
    }
    public void setAuthorName(String authorName) {this.authorName = authorName;}
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
}