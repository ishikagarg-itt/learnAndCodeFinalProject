package org.example.Services;

import org.example.Entity.FoodItem;
import org.example.Entity.Notification;
import org.example.Entity.RolloutMenuItem;
import org.example.Entity.VotedItem;
import org.example.Exception.NotFoundException;
import org.example.Repository.FoodItemRepository;
import org.example.Repository.RolloutMenuItemRepository;
import org.example.Repository.VotedItemRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChefService {
    private final RecommendationService recommendationService;
    private final NotificationService notificationService;
    private final FoodItemRepository foodItemRepository;
    private final RolloutMenuItemRepository rolloutMenuRepository;

    public ChefService(){
        recommendationService = new RecommendationService();
        foodItemRepository = new FoodItemRepository();
        notificationService = new NotificationService();
        rolloutMenuRepository = new RolloutMenuItemRepository();
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

            RolloutMenuItem rolloutMenuItem = buildRolloutMenuItem(foodItemId);
            rolloutMenuRepository.save(rolloutMenuItem);
        }

        Notification notification = notificationService.buildNotification("Roll_Out", "Chef has rolled out the menu, please choose from the menu");
        notificationService.sendNotification(notification);

        return "Menu rolled out successfully and notification sent to the employees";
    }

    public String askFeedback(){
        String message = "We are trying to improve your experience. Please provide your feedback and help us.";
        Notification notification = notificationService.buildNotification("Discard_Item", "");
        notificationService.sendNotification(notification);
        return "You have successfully sent notification for asking the feedback";
    }

    private RolloutMenuItem buildRolloutMenuItem(int foodItemId){
        RolloutMenuItem rolloutMenuItem = new RolloutMenuItem();
        rolloutMenuItem.setRolloutDate(new Date(Calendar.getInstance().getTimeInMillis()));
        FoodItem foodItem = new FoodItem();
        foodItem.setId(foodItemId);
        rolloutMenuItem.setFoodItem(foodItem);
        return rolloutMenuItem;
    }
}
