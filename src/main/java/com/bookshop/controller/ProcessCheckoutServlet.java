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
        String voucher = request.getParameter("voucher");
        String information = "Họ và tên: " + request.getParameter("fullName") +
                "\nSố điện thoại: "+ request.getParameter("phone") +
                "\nEmail: " + request.getParameter("email")+
                "\nĐịa chỉ: "+request.getParameter("address")+
                "\nQuận/Huyện: "+request.getParameter("district")+
                "\nTỉnh/Thành phố: "+request.getParameter("city")+
                "\nGhi chú:" + request.getParameter("note");
        session.setAttribute("information", information);

        double finalPrice = totalPrice;
        if (session.getAttribute("user") != null && "10".equals(voucher)) {
            finalPrice = totalPrice * 0.9;
        }

        if ("VNPAY".equals(paymentMethod)) {
            session.setAttribute("finalPrice", finalPrice);
            session.setAttribute("paymentMethod", "VNPAY");

            com.bookshop.model.Order order = new com.bookshop.model.Order();
            if (session.getAttribute("user") == null) {
                order.setUserId(0);
            } else {
                com.bookshop.model.User user = (com.bookshop.model.User) session.getAttribute("user");
                order.setUserId(user.getUserId());
            }
            order.setOrderStatus("Pending");
            order.setTotalPrice(finalPrice);
            order.setInformationDelivery(information);

            java.util.ArrayList<com.bookshop.model.CartItem> cartItemsCheckout = (java.util.ArrayList<com.bookshop.model.CartItem>) session.getAttribute("cartItemsCheckout");
            int orderId = com.bookshop.dao.OrderDao.insertOrder(order, cartItemsCheckout, "VNPAY");

            if (orderId > 0) {
                java.util.Map<String, String> vnp_Params = new java.util.HashMap<>();
                vnp_Params.put("vnp_Version", com.bookshop.util.VnPayConfig.vnp_Version);
                vnp_Params.put("vnp_Command", com.bookshop.util.VnPayConfig.vnp_Command);
                vnp_Params.put("vnp_TmnCode", com.bookshop.util.VnPayConfig.vnp_TmnCode);
                // VNPAY yêu cầu số tiền nhân với 100
                vnp_Params.put("vnp_Amount", String.valueOf((long) (finalPrice * 100)));
                vnp_Params.put("vnp_CurrCode", "VND");
                vnp_Params.put("vnp_TxnRef", String.valueOf(orderId));
                vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang " + orderId);
                vnp_Params.put("vnp_OrderType", "other");
                vnp_Params.put("vnp_Locale", "vn");
                
                String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/vnpay-return";
                vnp_Params.put("vnp_ReturnUrl", returnUrl);
                
                vnp_Params.put("vnp_IpAddr", com.bookshop.util.VnPayConfig.getIpAddress(request));

                java.util.Calendar cld = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
                java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
                String vnp_CreateDate = formatter.format(cld.getTime());
                vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

                String paymentUrl = com.bookshop.util.VnPayConfig.buildPaymentUrl(vnp_Params);
                System.out.println("VNPAY Redirect URL: " + paymentUrl);
                response.sendRedirect(paymentUrl);
                return;
            } else {
                session.setAttribute("message", "Không thể tạo đơn hàng. Vui lòng thử lại!");
                response.sendRedirect("cart");
                return;
            }
        }

        if(session.getAttribute("user") == null){
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
