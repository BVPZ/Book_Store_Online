package com.bookshop.dao;

import com.bookshop.model.Payment;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentDao {
    public static Payment getPaymentByOrderId(int orderId){

        String sql = "select * from payments where order_id = ?";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);){
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Payment payment = new Payment();
                payment.setOrderId(rs.getInt("order_id"));
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setPaymentStatus(rs.getString("payment_status"));
                payment.setPaymentMethod(rs.getString("payment_method"));
                return payment;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updatePaymentStatus(int orderId, String status) {
        String sql = "UPDATE payments SET payment_status = ? WHERE order_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
