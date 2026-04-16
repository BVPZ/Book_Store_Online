package com.bookshop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet("/process-checkout")
public class ProcessCheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String paymentMethod = request.getParameter("paymentMethod");
        double totalPrice = Double.parseDouble(request.getParameter("total"));
        session.setAttribute("finalPrice",totalPrice);
        String information = "Họ và tên: " + request.getParameter("fullName") +
                            "\nSố điện thoại: "+ request.getParameter("phone") +
                            "\nEmail: " + request.getParameter("email")+
                            "\nĐịa chỉ: "+request.getParameter("address")+
                            "\nQuận/Huyện: "+request.getParameter("district")+
                            "\nTỉnh/Thành phố: "+request.getParameter("city")+
                            "\nGhi chú:" + request.getParameter("note");
        session.setAttribute("information", information);
        if(session.getAttribute("user") == null){

            // Xử lý cho khách vãng lai

            switch (paymentMethod) {
                case "COD":
                    response.sendRedirect("order-status");
                    return;

                case "QRCODE":
                    String paymentUrl = "https://img.vietqr.io/image/MB-220052008-compact2.png?amount="+String.valueOf(totalPrice);
                    request.setAttribute("paymentUrl", paymentUrl);
                    request.setAttribute("paymentMethod", "QRCODE");
                    break;
            }
        }else{

            // Xử lý cho user

            switch (paymentMethod) {
                case "COD":
                    response.sendRedirect("order-status");
                    return;

                case "QRCODE":
                    String paymentUrl = "https://img.vietqr.io/image/MB-220052008-compact2.png?amount="+String.valueOf(totalPrice);
                    request.setAttribute("paymentUrl", paymentUrl);
                    request.setAttribute("paymentMethod", "QRCODE");
                    break;
            }
        }
        request.getRequestDispatcher("/view/payment.jsp").forward(request, response);
    }
}
