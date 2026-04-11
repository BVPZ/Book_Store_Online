package com.bookshop.dao;

import com.bookshop.model.Book;
import com.bookshop.model.Order;
import com.bookshop.model.OrderDetail;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    /**
     * Lấy danh sách đơn hàng của một người dùng và nạp luôn chi tiết từng món hàng.
     */
    public static ArrayList<Order> getOrdersByUserId(String userId) {
        ArrayList<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = mapResultSetToOrder(rs);
                // QUAN TRỌNG: Nạp danh sách sản phẩm vào đối tượng Order
                order.setOrderDetails(getDetailsByOrderId(order.getOrderId()));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy danh sách đơn hàng dựa trên tập hợp ID (Dùng cho khách vãng lai lưu Cookie).
     */
    public static ArrayList<Order> getOrdersByIds(List<String> orderIds) {
        ArrayList<Order> list = new ArrayList<>();
        if (orderIds == null || orderIds.isEmpty()) return list;

        StringBuilder query = new StringBuilder("SELECT * FROM orders WHERE order_id IN (");
        for (int i = 0; i < orderIds.size(); i++) {
            query.append(i == 0 ? "?" : ",?");
        }
        query.append(") ORDER BY order_date DESC");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {

            for (int i = 0; i < orderIds.size(); i++) {
                ps.setString(i + 1, orderIds.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = mapResultSetToOrder(rs);
                // QUAN TRỌNG: Nạp danh sách sản phẩm
                order.setOrderDetails(getDetailsByOrderId(order.getOrderId()));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Hàm phụ: Lấy chi tiết sản phẩm của một đơn hàng (Join với bảng Books để lấy ảnh và tiêu đề).
     */
    // Hàm lấy chi tiết sản phẩm (JOIN 3 bảng: order_details, books, authors)
    public static List<OrderDetail> getDetailsByOrderId(String orderId) {
        List<OrderDetail> details = new ArrayList<>();
        String sql = "SELECT od.*, b.title, b.image, a.author_name " +
                "FROM order_details od " +
                "JOIN books b ON od.book_id = b.book_id " +
                "LEFT JOIN authors a ON b.author_id = a.author_id " +
                "WHERE od.order_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setQuantity(String.valueOf(rs.getObject("quantity")));
                double rawPrice = rs.getDouble("price");
                od.setPrice(String.valueOf((long) rawPrice));

                // Tạo object Book chứa thông tin để hiển thị
                Book b = new Book();
                b.setTitle(rs.getString("title"));
                b.setImageUrl(rs.getString("image"));
                b.setAuthorName(rs.getString("author_name"));

                od.setBook(b);
                details.add(od);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return details;
    }



    /**
     * Hàm hỗ trợ ánh xạ dữ liệu từ ResultSet sang Object Order để tránh lặp code.
     */
    private static Order mapResultSetToOrder(ResultSet rs) throws Exception {
        Order order = new Order();
        order.setOrderId(rs.getString("order_id"));
        order.setOrderDate(rs.getString("order_date"));
        order.setOrderStatus(rs.getString("status"));
        order.setTotalPrice(rs.getString("total_price"));
        order.setUserId(rs.getString("user_id"));
        return order;
    }
}