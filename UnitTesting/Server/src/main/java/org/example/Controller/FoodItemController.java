package org.example.Controller;

import org.example.Entity.FoodItem;
import org.example.Services.FoodItemService;

import java.util.List;

public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(){
        foodItemService = new FoodItemService();
    }

    public FoodItem get(int id){
        return foodItemService.get(id);
    }

    public List<FoodItem> getAll(){
        return foodItemService.getAll();
    }

    public String add(FoodItem foodItem){
        return foodItemService.add(foodItem);
    }

    public FoodItem update(int id, FoodItem foodItem){
        return foodItemService.update(id, foodItem);
    }

    public void delete(int id){
        foodItemService.delete(id);
    }
}
