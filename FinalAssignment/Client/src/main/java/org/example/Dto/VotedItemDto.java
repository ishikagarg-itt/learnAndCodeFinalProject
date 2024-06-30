package org.example.Dto;

import java.util.Date;

public class VotedItemDto {
    private int id;
    private FoodItemResponseDto foodItem;
    private Date voting_date;
    private int totalVotes;

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

    public Date getVoting_date() {
        return voting_date;
    }

    public void setVoting_date(Date voting_date) {
        this.voting_date = voting_date;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
}
