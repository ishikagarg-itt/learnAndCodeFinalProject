package org.example.Dto;

public class RatingDto {
    private String rating;
    private String comment;
    private int foodItemId;
    private String feedback;
    private String tastePreference;
    private String recipe;

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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTastePreference() {
        return tastePreference;
    }

    public void setTastePreference(String tastePreference) {
        this.tastePreference = tastePreference;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
