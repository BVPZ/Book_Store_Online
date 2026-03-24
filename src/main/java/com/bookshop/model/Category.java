package com.bookshop.model;

public class Category {
    private String categoryId;
    private String categoryName;
    private String categoryDescription;
    public Category() {}
    public Category(String categoryId, String categoryName, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }
    public String getCategoryId() {
        return categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getCategoryDescription() {
        return categoryDescription;
    }
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

}
