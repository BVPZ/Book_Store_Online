package com.bookshop.model;

import com.bookshop.dao.AuthorDao;
import com.bookshop.dao.BookDao;

import java.sql.SQLException;

public class CartItem {
    private int CartItemId;
    private int CartId;
    private int BookId;
    private int quantity;

    public int getCartItemId() {
        return CartItemId;
    }
    public void setCartItemId(int CartItemId) {
        this.CartItemId = CartItemId;
    }

    public int getCartId() {
        return CartId;
    }
    public void setCartId(int CartId) {
        this.CartId = CartId;
    }

    public int getBookId() {
        return BookId;
    }
    public void setBookId(int BookId) {this.BookId = BookId;}

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() throws SQLException {
        return BookDao.getBookById(this.BookId).getPrice();
    }
    public String getImageUrl() throws SQLException {
        return  BookDao.getBookById(this.BookId).getImageUrl();
    }
    public String getTitle()  throws SQLException {
        return BookDao.getBookById(this.BookId).getTitle();
    }
    public String getAuthor()  throws SQLException {
        return AuthorDao.getAuthorbyId(BookDao.getBookById(this.BookId).getAuthorId()).getAuthorName();
    }
}
