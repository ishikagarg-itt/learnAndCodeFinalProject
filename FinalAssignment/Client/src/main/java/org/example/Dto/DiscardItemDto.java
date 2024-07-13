package org.example.Dto;

import java.time.LocalDate;

public class DiscardItemDto {
    private int id;
    private FoodItemResponseDto foodItem;
    private LocalDate discardDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FoodItemResponseDto getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItemResponseDto foodItem) {
        this.foodItem = foodItem;
    }

    public LocalDate getDiscardDate() {
        return discardDate;
    }

    public void setDiscardDate(LocalDate discardDate) {
        this.discardDate = discardDate;
    }
}
