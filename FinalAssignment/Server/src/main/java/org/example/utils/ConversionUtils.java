package org.example.utils;

import org.example.Dto.FoodItemDto;
import org.example.Entity.FoodItem;
import org.example.Entity.FoodItemType;
import org.example.Repository.FoodItemTypeRepository;

public class ConversionUtils {

    private static final FoodItemTypeRepository foodItemTypeRepository = new FoodItemTypeRepository();

    public static FoodItem convertFoodItemDtoToFoodItem(FoodItemDto foodItem){
        FoodItem itemToBeAdded = new FoodItem();
        FoodItemType foodItemType = new FoodItemType();
        foodItemType.setId(foodItemTypeRepository.getByName(foodItem.getType()).get());
        itemToBeAdded.setType(foodItemType);
        itemToBeAdded.setAvailabilityStatus(foodItem.isAvailabilityStatus());
        itemToBeAdded.setName(foodItem.getName());
        return itemToBeAdded;
    }
}
