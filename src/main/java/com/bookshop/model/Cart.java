package com.bookshop.model;

public class Cart {
    private String CartId;
    private String userId;
    public Cart(String cartId, String userId) {
        this.CartId = cartId;
        this.userId = userId;
    }
    public String getCartId() {
        return CartId;
    }
    public void setCartId(String CartId) {
        this.CartId = CartId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
