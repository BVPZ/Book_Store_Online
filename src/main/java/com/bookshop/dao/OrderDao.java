package com.bookshop.dao;

import com.bookshop.model.Book;
import com.bookshop.model.CartItem;
import com.bookshop.model.Order;
import com.bookshop.model.OrderDetail;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public static int countOrdersByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM orders WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int countPurchasedBooksByUserId(int userId) {
        String sql = "SELECT SUM(od.quantity) FROM order_details od JOIN orders o ON od.order_id = o.order_id WHERE o.user_id = ? AND o.status ='Completed' ";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Lấy danh sách đơn hàng của một người dùng và nạp luôn chi tiết từng món hàng.
     */
    public static ArrayList<OrderDetail> getDetailsByOrderId(Connection conn, int orderId) {
        ArrayList<OrderDetail> list = new ArrayList<>();
        // JOIN thêm bảng books để lấy title và image
        String sql = "SELECT od.*, b.title, b.image FROM order_details od " +
                "JOIN books b ON od.book_id = b.book_id WHERE od.order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetail od = new OrderDetail();
                    od.setBookId(rs.getInt("book_id"));
                    od.setQuantity(rs.getInt("quantity"));
                    od.setPrice(rs.getDouble("price"));

                    // Nạp thông tin sách để JSP hiển thị được ảnh/tên
                    Book b = new Book();
                    b.setTitle(rs.getString("title"));
                    b.setImageUrl(rs.getString("image"));
                    od.setBook(b);

                    list.add(od);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
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
    public static ArrayList<OrderDetail> getDetailsByOrderId(int orderId) {
        ArrayList<OrderDetail> details = new ArrayList<>();
        String sql = "SELECT od.*,b.book_id, b.title, b.image, a.author_name " +
                "FROM order_details od " +
                "JOIN books b ON od.book_id = b.book_id " +
                "LEFT JOIN authors a ON b.author_id = a.author_id " +
                "WHERE od.order_id = ?";
        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setQuantity(rs.getInt("quantity"));
                double rawPrice = rs.getDouble("price");
                od.setPrice(rawPrice);
                od.setBookId(rs.getInt("book_id"));
                od.setOrderId(rs.getInt("order_id"));
                od.setOrderDetailId(rs.getInt("order_detail_id"));
                // Tạo object Book chứa thông tin để hiển thị
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
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
        order.setOrderId(rs.getInt("order_id"));
        order.setOrderDate(rs.getString("order_date"));
        order.setOrderStatus(rs.getString("status"));
        order.setTotalPrice(rs.getDouble("total_price"));
        order.setUserId(rs.getInt("user_id"));
        order.setInformationDelivery(rs.getString("information_delivery"));
        return order;
    }
    public static int insertOrder(Order order, ArrayList<CartItem> items, String paymentMethod) {

        String sql = "INSERT INTO orders (user_id, order_date, total_price, status, information_delivery) VALUES (?, NOW(), ?, ?, ?)";

        Connection conn = null;
        int orderId = 0;
        try{
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if(order.getUserId() != 0){ps.setInt(1, order.getUserId());}
            else{ps.setNull(1, java.sql.Types.INTEGER);}
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getOrderStatus());
            ps.setString(4, order.getInformationDelivery());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                orderId = rs.getInt(1);
            }
            if(orderId == 0){
                throw new RuntimeException("Không lấy được orderId");
            }
            // xử lý từng item
            for (CartItem item : items) {
                // trừ stock
                String updateStock = "UPDATE books SET stock = stock - ? WHERE book_id = ? AND stock >= ?";
                try(PreparedStatement psStock = conn.prepareStatement(updateStock)){
                    psStock.setInt(1, item.getQuantity());
                    psStock.setInt(2, item.getBookId());
                    psStock.setInt(3, item.getQuantity());
                    System.out.println("stock");
                    System.out.println(item.getQuantity());
                    System.out.println(item.getBookId());
                    int rows = psStock.executeUpdate();
                    if(rows == 0){
                        throw new RuntimeException("Sản phẩm hết hàng");
                    }
                }
                // insert order_detail
                String sqlDetail = "INSERT INTO order_details(order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";
                try(PreparedStatement ps2 = conn.prepareStatement(sqlDetail)){
                    ps2.setInt(1, orderId);
                    ps2.setInt(2, item.getBookId());
                    ps2.setInt(3, item.getQuantity());
                    ps2.setDouble(4, item.getPrice());
                    ps2.executeUpdate();
                }

            }
            // insert payment
            String sql3 = "Insert into payments(order_id,payment_method) values(?,?)";
            try(PreparedStatement ps3 = conn.prepareStatement(sql3))
            {
                ps3.setInt(1, orderId);
                ps3.setString(2, paymentMethod);
                ps3.executeUpdate();
            }

            conn.commit();

        }catch (Exception e){
            try{
                if(conn != null) conn.rollback();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try{
                if(conn != null) conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return orderId;
    }
    public static ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC"; // Nên có Order By
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Dùng hàm map đã viết sẵn để lấy đủ ID, Date, Status, Price và ĐỊA CHỈ
                Order order = mapResultSetToOrder(rs);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    public static boolean updateStatus(Connection conn, int orderId, String newStatus) {
        String sqlUpdateOrder = "UPDATE orders SET status = ? WHERE order_id = ?";
        String sqlRestoreStock = "UPDATE books SET stock = stock + ? WHERE book_id = ?";

        PreparedStatement psOrder = null;
        PreparedStatement psStock = null;

        try {
            // 1. Cập nhật trạng thái đơn hàng
            psOrder = conn.prepareStatement(sqlUpdateOrder);
            psOrder.setString(1, newStatus);
            psOrder.setInt(2, orderId);
            int updatedRows = psOrder.executeUpdate();

            // 2. Nếu cập nhật trạng thái thành công và trạng thái mới là "Canceled" (Hủy)
            if (updatedRows > 0 && "Canceled".equalsIgnoreCase(newStatus)) {
                // Lấy danh sách sản phẩm của đơn hàng này
                // Sử dụng hàm getDetailsByOrderId bạn đã viết ở dòng 53
                ArrayList<OrderDetail> details = getDetailsByOrderId(conn, orderId);

                if (details != null && !details.isEmpty()) {
                    psStock = conn.prepareStatement(sqlRestoreStock);
                    for (OrderDetail detail : details) {
                        psStock.setInt(1, detail.getQuantity());
                        psStock.setInt(2, detail.getBookId());
                        psStock.addBatch(); // Dùng Batch để thực hiện nhiều lệnh một lúc
                    }
                    psStock.executeBatch();
                }
            }

            return updatedRows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // Chỉ đóng PreparedStatement, KHÔNG đóng conn ở đây
            try {
                if (psOrder != null) psOrder.close();
                if (psStock != null) psStock.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public static Order getOrderById(int orderId){
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setOrderDate(rs.getString("order_date"));
                order.setOrderStatus(rs.getString("status"));
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setInformationDelivery(rs.getString("information_delivery"));
                order.setUserId(rs.getInt("user_id"));
                return order;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Order> getOrdersByUserId(int userId) {
        ArrayList<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = mapResultSetToOrder(rs);
                // Nạp luôn chi tiết để hiển thị ảnh sản phẩm ở trang lịch sử
                order.setOrderDetails(getDetailsByOrderId(order.getOrderId()));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}