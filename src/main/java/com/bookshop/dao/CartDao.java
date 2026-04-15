package com.bookshop.dao;

import com.bookshop.model.Cart;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDao {
    public static Cart getCartByUserId(int userId) throws SQLException, ClassNotFoundException {
        try(Connection conn = DBConnection.getConnection();){
            String sql = "select * from cart where user_id = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            Cart cart = new Cart();
            if(rs.next()) {
            cart.setUserId(userId);
            cart.setCartId(rs.getInt("cart_id"));
            System.out.println("lay thong tin gio hang thanh cong");
            }
            return cart;
    }}
    public static void addCartByUserName(String userName) {
        String sql = "insert into cart(user_id) select user_id from users where username = ? ";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setString(1,userName);
            int rs = ps.executeUpdate();
            if(rs>0) System.out.println("Them gio hang thanh cong cho user"+userName);
            else System.out.println("Them gio hang that bai");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static boolean deleteCartByUserId(int userId){
        String sql = "delete from cart where user_id = ?";
        try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setInt(1,userId);
            int rs = ps.executeUpdate();
            if(rs>0) {
                System.out.println("Delete cart of user "+userId);
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
