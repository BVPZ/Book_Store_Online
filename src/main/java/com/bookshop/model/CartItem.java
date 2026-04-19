package com.bookshop.model;

import com.bookshop.dao.AuthorDao;

public class CartItem {
    private int cartItemId;
    private int cartId;
    private int bookId;
    private int quantity;
    private Book book;
    public int getCartItemId() {
        return cartItemId;
    }
    public void setCartItemId(int CartItemId) {this.cartItemId = CartItemId;}

    public int getCartId() {
        return cartId;
    }
    public void setCartId(int CartId) {
        this.cartId = CartId;
    }

    public int getBookId() {
        return this.bookId;
    }
    public void setBookId(int BookId) {this.bookId = BookId;}

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBook(Book book) {this.book = book;}
    public Book getBook() {return book;}

    public double getPrice()  {return book.getPrice();}
    public String getImageUrl()  {return  book.getImageUrl();}
    public String getTitle()   {return book.getTitle();}
    public String getAuthor()  {return AuthorDao.getAuthorbyId(book.getAuthorId()).getAuthorName();}
}
