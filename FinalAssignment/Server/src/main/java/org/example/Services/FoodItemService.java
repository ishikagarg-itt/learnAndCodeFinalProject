package org.example.Services;

import org.example.Entity.FoodItem;
import org.example.Repository.FoodItemRepository;

import java.util.List;

public class FoodItemService {

    private final FoodItemRepository foodItemRepository;

    public FoodItemService(){
        foodItemRepository = new FoodItemRepository();
    }

    public FoodItem add(FoodItem foodItem){
        return foodItemRepository.save(foodItem);
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
