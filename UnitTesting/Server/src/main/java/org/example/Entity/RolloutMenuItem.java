package org.example.Entity;

import java.util.Date;

public class RolloutMenuItem {
    private int id;
    private FoodItem foodItem;
    private Date rolloutDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public Date getRolloutDate() {
        return rolloutDate;
    }

    public void setRolloutDate(Date rolloutDate) {
        this.rolloutDate = rolloutDate;
    }
}
