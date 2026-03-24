package com.bookshop.model;

public class Author {
    private String authorId;
    private String authorName;
    private String bio;
    public Author() {}
    public Author(String authorId, String authorName, String bio) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.bio = bio;
    }
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
}
