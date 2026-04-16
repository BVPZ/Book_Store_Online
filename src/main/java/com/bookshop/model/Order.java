package com.bookshop.model;
import java.util.List;
import com.bookshop.model.OrderDetail;

public class Order {
    private int orderId;
    private String orderDate;
    private String orderStatus;
    private double totalPrice;
    private int userId;
    private String informationDelivery;
    public int getOrderId() {
        return this.orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return this.orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getInformationDelivery() {return informationDelivery;}
    public void setInformationDelivery(String informationDelivery) {this.informationDelivery = informationDelivery;}

    private List<OrderDetail> orderDetails;
    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }
}
