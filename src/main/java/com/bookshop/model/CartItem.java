package com.bookshop.model;

import com.bookshop.dao.AuthorDao;
import com.bookshop.dao.BookDao;

import java.sql.SQLException;

public class CartItem {
    private int CartItemId;
    private int CartId;
    private int BookId;
    private int quantity;
    private Book book;
    public int getCartItemId() {
        return CartItemId;
    }
    public void setCartItemId(int CartItemId) {this.CartItemId = CartItemId;}

    public int getCartId() {
        return CartId;
    }
    public void setCartId(int CartId) {
        this.CartId = CartId;
    }

    public int getBookId() {
        return this.book.getBookId();
    }
    public void setBookId(int BookId) {this.BookId = BookId;}

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBook(Book book) {this.book = book;}
    public Book getBook() {return book;}

    public double getPrice() throws SQLException {return book.getPrice();}
    public String getImageUrl() throws SQLException {return  book.getImageUrl();}
    public String getTitle()  throws SQLException {return book.getTitle();}
    public String getAuthor()  throws SQLException {return AuthorDao.getAuthorbyId(book.getAuthorId()).getAuthorName();}
}
