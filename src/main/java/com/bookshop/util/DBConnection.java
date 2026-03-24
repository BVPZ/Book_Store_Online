package com.bookshop.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://mysql-3419af58-bvpz2005-3c17.i.aivencloud.com:11071/defaultdb?useSSL=true&serverTimezone=UTC&characterEncoding=UTF-8";

    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_B6wMTYBSUV2OkPj2MP9";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công!");
            return conn;
        } catch (Exception e) {
            System.out.println("Kết nối thất bại!");
            e.printStackTrace();
            return null;
        }
    }
}