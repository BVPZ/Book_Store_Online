package com.bookshop.controller;

import com.bookshop.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        if(!password.equals(confirmPassword)) {
            session.setAttribute("error", "Mật khẩu ko giống nhau");
            response.sendRedirect("register");
            return;
        }
        else{
            try (Connection con = DBConnection.getConnection();){
                String checkSql = "SELECT * FROM users WHERE username = ?";
                PreparedStatement ps = con.prepareStatement(checkSql);
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    session.setAttribute("error", "Usename đã được sử dụng");
                    response.sendRedirect("register");
                    rs.close();
                    ps.close();
                    return;
                }
                String prepareSQL = "INSERT INTO users (username, password, email,full_name) VALUES (?, ?, ?,?)";
                PreparedStatement pst = con.prepareStatement(prepareSQL);
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, email);
                pst.setString(4, fullname);
                int success = pst.executeUpdate();
                response.sendRedirect("login");
                session.setAttribute("success", "Đăng ký thành công, bạn đã có thể đăng nhập");
                pst.close();
                if (success > 0) {
                    prepareSQL = "INSERT into cart(user_id) select user_id from users where username = ?";
                    pst = con.prepareStatement(prepareSQL);
                    pst.setString(1, username);
                    int rs1 = pst.executeUpdate();
                    if (rs1>0) {
                        System.out.println("Them nguoi dung va gio hang thanh cong");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("view/register.jsp").forward(request, response);
    }
}
