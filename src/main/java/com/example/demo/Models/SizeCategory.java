package com.example.demo.Models;

public class SizeCategory {
    private int sizeCategoryID;
  public  enum categoryName{
        Small, Medium, Large
    }
    private String categoryDescription;

    public SizeCategory() {
    }

    public SizeCategory(int sizeCategoryID, String categoryDescription) {
        this.sizeCategoryID = sizeCategoryID;
        this.categoryDescription = categoryDescription;
    }

    public int getSizeCategoryID() {
        return sizeCategoryID;
    }

    public void setSizeCategoryID(int sizeCategoryID) {
        this.sizeCategoryID = sizeCategoryID;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

}