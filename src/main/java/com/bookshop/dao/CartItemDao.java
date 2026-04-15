package com.bookshop.dao;

import com.bookshop.model.CartItem;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartItemDao {
    public static void AddItem(int cart_id, int book_id) throws SQLException, ClassNotFoundException,Exception {
        try (Connection conn = DBConnection.getConnection();) {
            String sql = "select * from cart_items where cart_id = ? and book_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cart_id);
            ps.setInt(2, book_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sql = "update cart_items set quantity = quantity + 1 where cart_id = ? and book_id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, cart_id);
                ps.setInt(2, book_id);
                ps.executeUpdate();
                return;
            }
            sql = "insert into cart_items (book_id,cart_id,quantity) values (?,?,1)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, book_id);
            ps.setInt(2, cart_id);
            ps.executeUpdate();
            ps.close();
            conn.close();
            return;
        }
    }
    public static ArrayList<CartItem> getAllCartItem(int cart_id) throws SQLException, ClassNotFoundException, Exception {
        try (Connection conn = DBConnection.getConnection();) {
            String sql = "select * from cart_items where cart_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cart_id);
            ResultSet rs = ps.executeQuery();
            ArrayList<CartItem> cartItems = new ArrayList<>();
            while (rs.next()) {
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
    public static CartItem getCartItemByCartItemId(int cartItemId) {
        CartItem cartItem = new CartItem();
        try (Connection conn = DBConnection.getConnection();) {
            String sql = "select * from cart_items where cart_item_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartItemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cartItem.setCartItemId(rs.getInt("cart_item_id"));
                cartItem.setBookId(rs.getInt("book_id"));
                cartItem.setCartId(rs.getInt("cart_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setBook(BookDao.getBookById(rs.getInt("book_id")));
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return  cartItem;
    }
    public static void deleteCartItemByCartItemId(int cartItemId) {
        try(Connection conn = DBConnection.getConnection()){
            String sql = "delete from cart_items where cart_item_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartItemId);
            ps.executeUpdate();
            ps.close();
            return;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static boolean updateCartItemQuantity(int cartItemId, int quantity) {
        String sql = "update cart_items set quantity = ? where cart_item_id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            int rs = ps.executeUpdate();
            if(rs > 0){
                System.out.println("Cap nhat so luong trong gio hang");
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
