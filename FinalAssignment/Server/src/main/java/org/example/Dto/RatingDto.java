package org.example.Dto;

import org.example.Entity.FoodItem;
import org.example.Entity.User;

import java.util.Date;

public class RatingDto {
    private String rating;
    private String comment;
    private int foodItemId;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }
}
