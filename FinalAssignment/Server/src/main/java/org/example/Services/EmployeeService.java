package org.example.Services;

import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.RatingDto;
import org.example.Entity.FoodItem;
import org.example.Entity.Notification;
import org.example.Entity.Rating;
import org.example.Entity.VotedItem;
import org.example.Exception.NotFoundException;
import org.example.Repository.FoodItemRepository;
import org.example.Repository.RatingRepository;
import org.example.Repository.VotedItemRepository;

import java.util.List;

public class EmployeeService {
    private final VotedItemRepository votedItemRepository;
    private final RatingRepository ratingRepository;
    private final FoodItemRepository foodItemRepository;
    private final NotificationService notificationService;

    public EmployeeService(){
        votedItemRepository = new VotedItemRepository();
        ratingRepository = new RatingRepository();
        foodItemRepository = new FoodItemRepository();
        notificationService = new NotificationService();
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

    public String provideRating(RatingDto rating, String username){
        boolean isExists = foodItemRepository.isExist(rating.getFoodItemId());
        if(!isExists){
            throw new NotFoundException("The food Item you rated does not exist");
        }

        if (ratingRepository.hasUserRatedToday(username, rating.getFoodItemId())) {
            throw new IllegalStateException("You have already rated this food item today.");
        }

        Rating ratingTobeAdded = new Rating();
        ratingTobeAdded.setRating(rating.getRating());
        ratingTobeAdded.setComment(rating.getComment());
        FoodItem foodItem = new FoodItem();
        foodItem.setId(rating.getFoodItemId());
        ratingTobeAdded.setFoodItem(foodItem);
        ratingRepository.save(ratingTobeAdded, username);
        return "You have rated the item successfully";
    }

    public List<Notification> viewNotifications(){
        return notificationService.getNotifications();
    }
}
