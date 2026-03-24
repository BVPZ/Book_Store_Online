package com.bookshop.model;

public class OrderDetail {
    private String orderDetailId;
    private String orderId;
    private String bookId;
    private String quantity;
    private String price;
    public String getOrderDetailId() {
        return orderDetailId;
    }
    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}
