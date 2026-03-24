package com.bookshop.model;

public class CartItem {
    private String CartItemId;
    private String CartId;
    private String BookId;
    private String quantity;
    public String getCartItemId() {
        return CartItemId;
    }
    public void setCartItemId(String CartItemId) {
        this.CartItemId = CartItemId;
    }
    public String getCartId() {
        return CartId;
    }
    public void setCartId(String CartId) {
        this.CartId = CartId;
    }
    public String getBookId() {
        return BookId;
    }
    public void setBookId(String BookId) {
        this.BookId = BookId;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
