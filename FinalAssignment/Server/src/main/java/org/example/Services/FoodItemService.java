package org.example.Services;

import org.example.Entity.FoodItem;
import org.example.Entity.ItemAudit;
import org.example.Entity.Notification;
import org.example.Repository.FoodItemRepository;
import org.example.Repository.ItemAuditRepository;

import java.util.List;

public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final NotificationService notificationService;
    private final ItemAuditRepository itemAuditRepository;

    public FoodItemService(){
        foodItemRepository = new FoodItemRepository();
        notificationService = new NotificationService();
        itemAuditRepository = new ItemAuditRepository();
    }

    public FoodItem add(FoodItem foodItem){
        FoodItem addedFoodItem = foodItemRepository.save(foodItem);
        ItemAudit itemAudit = buildItemAudit(addedFoodItem.getId());
        itemAuditRepository.save(itemAudit);
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

    private ItemAudit buildItemAudit(int id){
        ItemAudit itemAudit = new ItemAudit();
        itemAudit.setFoodItemId(id);
        itemAudit.setAverageRating(5.0);
        itemAudit.setAverageSentiment(5.0);
        return itemAudit;
    }
}
