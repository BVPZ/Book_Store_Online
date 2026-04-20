package com.bookshop.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.bookshop.dao.CartDao;
import com.bookshop.dao.CartItemDao;
import com.bookshop.model.Book;
import com.bookshop.model.Cart;
import com.bookshop.model.CartItem;
import com.bookshop.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Book book =(Book) session.getAttribute("book");

        if (user == null) {
            // 1. Lấy danh sách từ session
            ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cartItems");




            // 2. SỬA LỖI TẠI ĐÂY: Nếu null thì phải khởi tạo mới thay vì gọi .isEmpty()
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }




            // 3. Kiểm tra xem sách đã có trong giỏ chưa
            boolean isExist = false;
            for (CartItem item : cartItems) {
                if (item.getBookId() == book.getBookId()) {
                    item.setQuantity(item.getQuantity() + 1);
                    isExist = true;
                    break;
                }
            }




            // 4. Nếu là sách mới thì mới thêm vào list
            if (!isExist) {
                CartItem newItem = new CartItem();
                newItem.setBook(book);
                newItem.setBookId(book.getBookId());
                newItem.setQuantity(1);
                newItem.setPrice(book.getPrice()); // Đảm bảo có giá để lưu vào database sau này
                cartItems.add(newItem);
            }




            // 5. Lưu lại vào session để ProcessCheckoutServlet có thể lấy ra
            session.setAttribute("cartItems", cartItems);
            session.setAttribute("cartItemsCheckout", cartItems);
            System.out.println("Giỏ hàng vãng lai hiện có: " + cartItems.size() + " loại sản phẩm");}


            else{
            try{
                Cart cart = CartDao.getCartByUserId(user.getUserId());
                CartItemDao.AddItem(cart.getCartId(),book.getBookId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        session.setAttribute("message","success");
        response.sendRedirect("book-detail?id="+book.getBookId());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
