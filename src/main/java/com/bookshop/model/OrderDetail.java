package com.bookshop.model;

public class OrderDetail {
    private int orderDetailId;
    private int orderId;
    private int bookId;
    private int quantity;
    private double price;
    public int getOrderDetailId() {
        return orderDetailId;
    }
    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }


    private Book book;
    public Book getBook() { return book;}
    public void setBook(Book book) { this.book = book; }
}
