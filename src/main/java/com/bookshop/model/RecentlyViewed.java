package com.bookshop.model;

import java.sql.Timestamp;

public class RecentlyViewed {
    private int userId;
    private int bookId;
    private Timestamp viewedAt;

    private Book book;

    public RecentlyViewed() {
    }

    public RecentlyViewed(int userId, int bookId, Timestamp viewedAt) {
        this.userId = userId;
        this.bookId = bookId;
        this.viewedAt = viewedAt;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Timestamp getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(Timestamp viewedAt) {
        this.viewedAt = viewedAt;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
