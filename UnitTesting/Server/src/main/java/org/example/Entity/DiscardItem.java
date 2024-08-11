package org.example.Entity;

import java.time.LocalDate;

public class DiscardItem {
    private int id;
    private FoodItem foodItem;
    private LocalDate discardDate;

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

    public LocalDate getDiscardDate() {
        return discardDate;
    }

    public void setDiscardDate(LocalDate discardDate) {
        this.discardDate = discardDate;
    }
}
