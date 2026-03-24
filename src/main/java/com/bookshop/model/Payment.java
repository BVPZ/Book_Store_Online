package com.bookshop.model;

public class Payment {
    private String paymentId;
    private String paymentDate;
    private String paymentStatus;
    private String paymentMethod;
    private String OrderId;
    public String getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public String getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getOrderId() {
        return OrderId;
    }
    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
