package org.example.Dto;

public class EmployeeMenuDto {
    private FoodItemResponseDto foodItem;
    private double averageRating;
    private String comment;

    public FoodItemResponseDto getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItemResponseDto foodItem) {
        this.foodItem = foodItem;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
