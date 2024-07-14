package org.example.utils;

import org.example.Dto.FoodItemDto;
import org.example.Entity.FoodItem;
import org.example.Entity.FoodItemType;
import org.example.Entity.MealPreference;
import org.example.Entity.Region;
import org.example.Entity.SpiceLevel;
import org.example.Repository.FoodItemTypeRepository;
import org.example.Repository.MealPreferenceRepository;
import org.example.Repository.RegionRepository;
import org.example.Repository.SpiceLevelRepository;

public class ConversionUtils {

    private static final FoodItemTypeRepository foodItemTypeRepository = new FoodItemTypeRepository();
    private static final MealPreferenceRepository mealPreferenceRepository = new MealPreferenceRepository();
    private static final RegionRepository regionRepository = new RegionRepository();
    private static final SpiceLevelRepository spiceLevelRepository = new SpiceLevelRepository();

    public static FoodItem convertFoodItemDtoToFoodItem(FoodItemDto foodItem){
        FoodItem itemToBeAdded = new FoodItem();
        FoodItemType foodItemType = new FoodItemType();
        foodItemType.setId(foodItemTypeRepository.getByName(foodItem.getType()).get());
        itemToBeAdded.setType(foodItemType);
        Region region = new Region();
        region.setId(regionRepository.getByName(foodItem.getRegion()).get());
        itemToBeAdded.setRegion(region);

        MealPreference mealPreference = new MealPreference();
        mealPreference.setId(mealPreferenceRepository.getByName(foodItem.getMealPreference()).get());
        itemToBeAdded.setMealPreference(mealPreference);

        SpiceLevel spiceLevel = new SpiceLevel();
        spiceLevel.setId(spiceLevelRepository.getByName(foodItem.getSpiceLevel()).get());
        itemToBeAdded.setSpiceLevel(spiceLevel);

        itemToBeAdded.setAvailabilityStatus(foodItem.isAvailabilityStatus());
        itemToBeAdded.setName(foodItem.getName());
        itemToBeAdded.setSweetTooth(foodItem.isSweetTooth());
        return itemToBeAdded;
    }

    public static FoodItemDto convertFoodItemToFoodItemDto(FoodItem addedFoodItem) {
        FoodItemDto addedFoodItemDto = new FoodItemDto();
        addedFoodItemDto.setName(addedFoodItem.getName());
        addedFoodItemDto.setType(addedFoodItem.getType().getType());
        addedFoodItemDto.setAvailabilityStatus(addedFoodItem.isAvailabilityStatus());
        return addedFoodItemDto;
    }
}
