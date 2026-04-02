package com.bookshop.dao;

import com.bookshop.model.Cart;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDao {
    public static Cart getCartByUserId(int userId) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        String sql = "select * from cart where user_id = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,userId);
        ResultSet rs = ps.executeQuery();
        Cart cart = new Cart();
        rs.next();
        cart.setUserId(userId);
        cart.setCartId(rs.getInt("cart_id"));
        if (cart!=null) System.out.println("lay thong tin gio hang thanh cong");
        return cart;
    }
}
