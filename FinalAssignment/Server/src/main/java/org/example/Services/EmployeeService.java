package org.example.Services;

import org.example.Dto.EmployeeMenuDto;
import org.example.Entity.FoodItem;
import org.example.Entity.Rating;
import org.example.Entity.VotedItem;
import org.example.Exception.NotFoundException;
import org.example.Repository.VotedItemRepository;

import java.util.List;

public class EmployeeService {
    private final VotedItemRepository votedItemRepository;

    public EmployeeService(){
        votedItemRepository = new VotedItemRepository();
    }

    public List<EmployeeMenuDto> getRollOutMenu(){
        List<EmployeeMenuDto> foodItems = votedItemRepository.getMenuForEmployee();
        return foodItems;
    }

    public String chooseItems(List<Integer> chosenFoodItemIds){
        for(int foodItemId : chosenFoodItemIds){
            VotedItem votedItem = votedItemRepository.getItemsToBeVotedForToday(foodItemId);
            if(votedItem == null){
                throw new NotFoundException("Food item has not been rolled out");
            }
            votedItemRepository.update(votedItem);
        }
        return "You have chosen the items to be prepared for tomorrow";
    }

    public String provideRating(Rating rating){
        return null;
    }
}
