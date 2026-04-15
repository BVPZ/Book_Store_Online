package com.bookshop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String paymentMethod = request.getParameter("paymentMethod");
        if(session.getAttribute("user") == null){
            // Xử lý cho khách vãng lai

            switch (paymentMethod) {
                case "COD":
                    break;

                case "MOMO":
                    break;

                case "ZALOPAY":
                    break;

                case "QRCODE":
                    break;
            }
        }else{
            // Xử lý cho user

            double totalPrice = Double.parseDouble(request.getParameter("total"));
            switch (paymentMethod) {
                case "COD":
                    // xử lý COD
                    break;

                case "MOMO":
                    // redirect sang MoMo
                    break;

                case "ZALOPAY":
                    // xử lý ZaloPay
                    break;

                case "QRCODE":
                    String paymentUrl = "https://img.vietqr.io/image/MB-220052008-compact2.png?amount="+String.valueOf(totalPrice);
                    System.out.println(paymentUrl);
                    request.setAttribute("paymentUrl", paymentUrl);
                    request.setAttribute("paymentMethod", "QRCODE");
                    break;
            }
        }
        request.getRequestDispatcher("/view/payment.jsp").forward(request, response);
    }
}
