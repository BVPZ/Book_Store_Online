package com.bookshop.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class DBConnection {

    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl("jdbc:mysql://mysql-3419af58-bvpz2005-3c17.i.aivencloud.com:11071/defaultdb");
            config.setUsername("avnadmin");
            config.setPassword("AVNS_B6wMTYBSUV2OkPj2MP9");
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // cực quan trọng cho cloud DB
            config.addDataSourceProperty("sslMode", "REQUIRED");

            // fix connection chết
            config.setConnectionTestQuery("SELECT 1");
            config.setValidationTimeout(5000);

            // pool
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);

            // giảm lifetime để tránh bị DB kill
            config.setMaxLifetime(600000); // 10 phút (quan trọng)
            config.setIdleTimeout(300000); // 5 phút

            // debug leak
            config.setLeakDetectionThreshold(20000);

            dataSource = new HikariDataSource(config);

            System.out.println("Pool OK");

        } catch (Exception e) {
            throw new RuntimeException(" Init DB failed", e);
        }
    }


    public static Connection getConnection()  {
        try{
            return dataSource.getConnection();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}