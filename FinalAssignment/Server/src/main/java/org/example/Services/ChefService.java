package org.example.Services;

import org.example.Dto.CommentSentiment;
import org.example.Entity.FoodItem;
import org.example.Entity.Notification;
import org.example.Entity.VotedItem;
import org.example.Exception.NotFoundException;
import org.example.Repository.FoodItemRepository;
import org.example.Repository.VotedItemRepository;
import org.example.utils.SentimentAnalysis;

import java.util.List;

public class ChefService {
    private final RecommendationService recommendationService;
    private final NotificationService notificationService;
    private final VotedItemRepository votedItemRepository;
    private final FoodItemRepository foodItemRepository;

    public ChefService(){
        recommendationService = new RecommendationService();
        votedItemRepository = new VotedItemRepository();
        foodItemRepository = new FoodItemRepository();
        notificationService = new NotificationService();
    }
    public List<FoodItem> getRecommendation(){
        return recommendationService.getRecommendation();
    }

    public String rollOutMenu(List<Integer> foodItemIds){
        for(Integer foodItemId : foodItemIds){
            boolean exists = foodItemRepository.isExist(foodItemId);
            if (exists) {
                System.out.println("Food item exists.");
            } else {
                throw new NotFoundException("Food item does not exist.");
            }

            VotedItem votedItem = buildVotedItem(foodItemId);
            votedItemRepository.save(votedItem);
        }

        Notification notification = notificationService.buildNotification("Roll_Out", "Chef has rolled out the menu, please choose from the menu");
        notificationService.sendNotification(notification);

        return "Menu rolled out successfully and notification sent to the employees";
    }

    private VotedItem buildVotedItem(int foodItemId){
        VotedItem votedItem = new VotedItem();
        votedItem.setTotalVotes(0);
        FoodItem foodItem = new FoodItem();
        foodItem.setId(foodItemId);
        votedItem.setFoodItem(foodItem);
        return votedItem;
    }
}
