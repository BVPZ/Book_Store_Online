package com.bookshop.model;

public class Book {

    private int bookId;
    private String title;
    private double price;
    private int stock;
    private String description;
    private String image;
    private int publishedYear;
    private int authorId;
    public Book() {}
    public Book(int bookId, String title, double price, int stock,
                String description, String image,int publishedYear, int authorId) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.image = image;
        this.publishedYear = publishedYear;
        this.authorId = authorId;

    }
    public int getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getPublishedYear() {
        return publishedYear;
    }
    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

}
