package com.bookshop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import com.bookshop.model.Order;
import com.bookshop.model.CartItem;
import com.bookshop.model.User;
import com.bookshop.dao.OrderDao;
import java.util.ArrayList;
import java.io.IOException;



@WebServlet("/process-checkout")
public class ProcessCheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String paymentMethod = request.getParameter("paymentMethod");
        double totalPrice = Double.parseDouble(request.getParameter("total"));
        String voucher = request.getParameter("voucher");
        String information = "Họ và tên: " + request.getParameter("fullName") +
                "\nSố điện thoại: "+ request.getParameter("phone") +
                "\nEmail: " + request.getParameter("email")+
                "\nĐịa chỉ: "+request.getParameter("address")+
                "\nQuận/Huyện: "+request.getParameter("district")+
                "\nTỉnh/Thành phố: "+request.getParameter("city")+
                "\nGhi chú:" + request.getParameter("note");
        session.setAttribute("information", information);

        int orderId = 0;
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cartItems");
        if(session.getAttribute("user") == null){
            Order guestOrder = new Order();
            guestOrder.setUserId(0);
            guestOrder.setTotalPrice(totalPrice);
            guestOrder.setOrderStatus("Pending");
            guestOrder.setInformationDelivery(information);
            orderId = OrderDao.insertOrder(guestOrder, cartItems, paymentMethod);

            // --- PHẦN NÂNG CẤP COOKIE ---
            String orderHistory = "";
            Cookie[] cookies = request.getCookies();

            // Tìm xem đã có cookie lịch sử đơn hàng chưa
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("guestOrderHistory")) {
                        orderHistory = c.getValue();
                        break;
                    }
                }
            }

            // Nối thêm orderId mới vào chuỗi (dùng dấu gạch đứng | làm phân cách)
            if (orderHistory.isEmpty()) {
                orderHistory = String.valueOf(orderId);
            } else {
                // Kiểm tra tránh trùng lặp nếu cần, ở đây ta cứ nối tiếp vào
                orderHistory += "|" + orderId;
            }

            // Tạo cookie mới lưu chuỗi ID (ví dụ: "101|102|105")
            Cookie historyCookie = new Cookie("guestOrderHistory", orderHistory);
            historyCookie.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
            historyCookie.setPath("/");
            response.addCookie(historyCookie);


            switch (paymentMethod) {
                case "COD":
                    session.setAttribute("finalPrice",totalPrice);
                    response.sendRedirect("order-status");
                    session.setAttribute("paymentMethod", "COD");
                    return;

                case "QRCODE":
                    session.setAttribute("finalPrice",totalPrice);
                    String paymentUrl = "https://img.vietqr.io/image/MB-220052008-compact2.png?amount="+String.valueOf(totalPrice);
                    request.setAttribute("paymentUrl", paymentUrl);
                    session.setAttribute("paymentMethod", "VIET-QR");
                    break;
            }
        }else{
            double finalPrice = totalPrice;
            // Xử lý cho user
            if("10".equals(voucher)){
                finalPrice = totalPrice*0.9;
            }
            switch (paymentMethod) {
                case "COD":
                    session.setAttribute("finalPrice",finalPrice);
                    session.setAttribute("paymentMethod", "COD");
                    response.sendRedirect("order-status?paymentMethod=COD");

                    return;

                case "QRCODE":
                    session.setAttribute("finalPrice",finalPrice);
                    session.setAttribute("paymentMethod", "VIET-QR");
                    String paymentUrl = "https://img.vietqr.io/image/MB-220052008-compact2.png?amount="+String.valueOf(finalPrice);
                    request.setAttribute("paymentUrl", paymentUrl);
                    break;
            }
        }
        request.getRequestDispatcher("/view/payment.jsp").forward(request, response);
    }
}