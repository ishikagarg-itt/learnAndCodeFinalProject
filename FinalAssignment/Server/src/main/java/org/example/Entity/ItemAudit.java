package org.example.Entity;

public class ItemAudit {
    private int id;
    private int foodItemId;
    private double averageRating;
    private double averageSentiment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public double getAverageSentiment() {
        return averageSentiment;
    }

    public void setAverageSentiment(double averageSentiment) {
        this.averageSentiment = averageSentiment;
    }
}
