package com.bookshop.dao;
import java.sql.PreparedStatement;

import com.bookshop.model.*;
import com.bookshop.util.*;
import com.mysql.cj.Session;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    public static User login(String username, String password) throws Exception {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
        }
        return null;
    }
    public static ArrayList<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        ArrayList<User> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            rs.next(); //skip admin
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }
    public static User getUserById(int userId) {
        User user = new User();
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
            return null;
        }catch (Exception e)
            {
            e.printStackTrace();
            }
        return null;
    }
    public static void editUser(int user_id,String full_name,String email,String phone,String address) {
        String sql = "update users set email = ?, full_name = ?, address = ?, phone = ? where user_id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,email);
            ps.setString(2,full_name);
            ps.setString(3,address);
            ps.setString(4,phone);
            ps.setInt(5,user_id);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static boolean deleteUser(int user_id) {
        String sql = "delete from users where user_id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            CartDao.deleteCartByUserId(user_id);
            ps.setInt(1, user_id);
            int rs = ps.executeUpdate();
            if(rs>0){
                System.out.println("Deleted use:" + user_id);
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isUsernameExists(String username) {
        String sql = "select * from users where username = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean addUser(User user) {
        String sql = "insert into users (user_id,username,password,email,full_name," +
                "address,phone,role) values (?,?,?,?,?,?,?,?) ";
        try(Connection conn = DBConnection.getConnection()
        ;PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getFullName());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getPhone());
            ps.setString(8, user.getRole());
            int rs = ps.executeUpdate();
            if(rs>0) {
                System.out.println("Them nguoi dung moi thanh cong");
                return true;
            }
            else System.out.println("Them nguoi dung moi that bai");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
