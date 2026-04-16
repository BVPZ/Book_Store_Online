package com.bookshop.dao;

import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdminDao {

    // Lấy doanh thu 5 tháng gần nhất
    public static Map<String, Double> getRevenueLast5Months() {
        Map<String, Double> data = new LinkedHashMap<>();
        YearMonth currentMonth = YearMonth.now();
        
        // Khởi tạo 5 tháng gần nhất (VD: T12/2023, T1/2024, ...)
        for (int i = 4; i >= 0; i--) {
            YearMonth ym = currentMonth.minusMonths(i);
            data.put("T" + ym.getMonthValue() + "/" + ym.getYear(), 0.0);
        }

        String sql = "SELECT MONTH(order_date) as m, YEAR(order_date) as y, SUM(total_price) as revenue " +
                "FROM orders " +
                "WHERE status = 'Completed' AND order_date >= DATE_SUB(LAST_DAY(NOW()), INTERVAL 6 MONTH) " +
                "GROUP BY y, m";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String key = "T" + rs.getInt("m") + "/" + rs.getInt("y");
                if (data.containsKey(key)) {
                    data.put(key, rs.getDouble("revenue"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // Lấy số đơn hàng 5 tháng gần nhất
    public static Map<String, Integer> getOrdersLast5Months() {
        Map<String, Integer> data = new LinkedHashMap<>();
        YearMonth currentMonth = YearMonth.now();
        
        for (int i = 4; i >= 0; i--) {
            YearMonth ym = currentMonth.minusMonths(i);
            data.put("T" + ym.getMonthValue() + "/" + ym.getYear(), 0);
        }

        String sql = "SELECT MONTH(order_date) as m, YEAR(order_date) as y, COUNT(order_id) as total_orders " +
                "FROM orders " +
                "WHERE order_date >= DATE_SUB(LAST_DAY(NOW()), INTERVAL 6 MONTH) " +
                "GROUP BY y, m";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String key = "T" + rs.getInt("m") + "/" + rs.getInt("y");
                if (data.containsKey(key)) {
                    data.put(key, rs.getInt("total_orders"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // Đếm số lượng sách theo từng thể loại
    public static Map<String, Integer> getBooksCountByCategory() {
        Map<String, Integer> counts = new LinkedHashMap<>();

        // Join bảng categories với book_categories để đếm tổng số đầu sách mỗi loại
        String sql = "SELECT c.category_name, COUNT(bc.book_id) as total_books " +
                "FROM categories c " +
                "LEFT JOIN book_categories bc ON c.category_id = bc.category_id " +
                "GROUP BY c.category_id, c.category_name " +
                "ORDER BY total_books DESC LIMIT 5";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                counts.put(rs.getString("category_name"), rs.getInt("total_books"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counts;
    }
}
