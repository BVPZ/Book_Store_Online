package com.bookshop.dao;

import com.bookshop.model.CartItem;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartItemDao {
    public static void AddItem(int cart_id, int book_id) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        String sql = "insert into cart_items (book_id,cart_id,quantity) values (?,?,1)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,book_id);
        ps.setInt(2,cart_id);
        ps.executeUpdate();
        ps.close();
        conn.close();
        return;
    }
    public static ArrayList<CartItem> getAllCartItem(int cart_id) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        String sql = "select * from cart_items where cart_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,cart_id);
        ResultSet rs = ps.executeQuery();
        ArrayList<CartItem> cartItems = new ArrayList<>();
        while(rs.next()){
            CartItem cartItem = new CartItem();
            cartItem.setCartItemId(rs.getInt("cart_item_id"));
            cartItem.setBookId(rs.getInt("book_id"));
            cartItem.setCartId(rs.getInt("cart_id"));
            cartItem.setQuantity(rs.getInt("quantity"));
            cartItem.setBook(BookDao.getBookById(rs.getInt("book_id")));
            cartItems.add(cartItem);
            System.out.println("Da lay dc 1 item");
        }
        return cartItems;
    }

}
