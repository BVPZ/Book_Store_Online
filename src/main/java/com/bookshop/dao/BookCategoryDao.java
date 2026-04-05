package com.bookshop.dao;

import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookCategoryDao {
    //    public static ArrayList<String> getAllGenresByBookId(int bookId) {
//        ArrayList<String> genres = new ArrayList<>();
//        try(Connection con = DBConnection.getConnection()) {
//            String sql = "select * from book_categories where book_id = ?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setInt(1, bookId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                genres.add(CategoryDao.getCategoryById(rs.getInt("category_id")).getCategoryName());
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        System.out.println("Lay the loai sach thanh cong");
//        return genres;
//    }
    public static ArrayList<String> getAllGenresByBookId(int bookId) {
        ArrayList<String> genres = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM book_category WHERE bookId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                genres.add(CategoryDao.getCategoryById(rs.getInt("categoryId")).getCategoryName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Lấy thể loại sách thành công");
        return genres;
    }
}
