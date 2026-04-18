package com.bookshop.model;

public class Payment {
    private int paymentId;
    private String paymentDate;
    private String paymentStatus;
    private String paymentMethod;
    private int OrderId;

    public int getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(int paymentId) {
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

    public int getOrderId() {
        return OrderId;
    }
    public void setOrderId(int orderId) {
        OrderId = orderId;
    }
}
