package com.appservice.dto;

public class ProductsDTO {

    private String name;
    private String cuisineId;
    private String mealTimeId;
    private String vegOrNonVegId;
    private String categoryId;
    private String subCategoryId;
    private String price;
    private String restaurantId;
    private String createdBy;
    private String updatedBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(String cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getMealTimeId() {
        return mealTimeId;
    }

    public void setMealTimeId(String mealTimeId) {
        this.mealTimeId = mealTimeId;
    }

    public String getVegOrNonVegId() {
        return vegOrNonVegId;
    }

    public void setVegOrNonVegId(String vegOrNonVegId) {
        this.vegOrNonVegId = vegOrNonVegId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
