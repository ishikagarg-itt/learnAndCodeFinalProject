package org.example.Controller;

import org.example.Entity.FoodItem;
import org.example.Services.FoodItemService;

public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(){
        foodItemService = new FoodItemService();
    }

    public FoodItem get(int id){
        return foodItemService.get(id);
    }

    public FoodItem add(FoodItem foodItem){
        return foodItemService.add(foodItem);
    }

    public FoodItem update(int id, FoodItem foodItem){
        return foodItemService.update(id, foodItem);
    }

    public void delete(int id){
        foodItemService.delete(id);
    }
}
