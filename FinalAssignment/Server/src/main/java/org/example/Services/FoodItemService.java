package org.example.Services;

import org.example.Entity.FoodItem;
import org.example.Entity.Notification;
import org.example.Repository.FoodItemRepository;

import java.util.List;

public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final NotificationService notificationService;

    public FoodItemService(){
        foodItemRepository = new FoodItemRepository();
        notificationService = new NotificationService();
    }

    public FoodItem add(FoodItem foodItem){
        FoodItem addedFoodItem = foodItemRepository.save(foodItem);
        Notification notification = notificationService.buildNotification("Add_Item", "A new item has been added to the menu");
        notificationService.sendNotification(notification);
        return addedFoodItem;
    }

    public FoodItem get(int id){
        return foodItemRepository.getById(id);
    }

    public List<FoodItem> getAll(){
        return foodItemRepository.getAll();
    }

    public void delete(int id){
        foodItemRepository.delete(id);
    }

    public FoodItem update(int id, FoodItem foodItem){
        return foodItemRepository.update(id, foodItem);
    }
}
