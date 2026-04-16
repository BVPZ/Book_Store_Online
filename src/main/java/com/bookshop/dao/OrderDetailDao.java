package com.bookshop.dao;

import com.bookshop.model.OrderDetail;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class OrderDetailDao {
//    public ArrayList<OrderDetail> getOrderDetailByOrderId(Integer orderId){
//
//    }
    public static void insertNewOrderDetail(OrderDetail orderDetail){
        String sql = "INSERT INTO order_details(order_id,book_id,quantity,price) VALUES (?,?,?,?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setInt(1,orderDetail.getOrderId());
            ps.setInt(2,orderDetail.getBookId());
            ps.setInt(3,orderDetail.getQuantity());
            ps.setDouble(4,orderDetail.getPrice());
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
