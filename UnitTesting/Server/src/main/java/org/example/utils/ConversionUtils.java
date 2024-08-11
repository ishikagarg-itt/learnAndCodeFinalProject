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

    public static FoodItem convertFoodItemDtoToFoodItem(FoodItemDto foodItemDto){
        FoodItem foodItem = new FoodItem();
        FoodItemType foodItemType = new FoodItemType();
        foodItemType.setId(foodItemTypeRepository.getByName(foodItemDto.getType()).get());
        foodItem.setType(foodItemType);
        Region region = new Region();
        region.setId(regionRepository.getByName(foodItemDto.getRegion()).get());
        foodItem.setRegion(region);

        MealPreference mealPreference = new MealPreference();
        mealPreference.setId(mealPreferenceRepository.getByName(foodItemDto.getMealPreference()).get());
        foodItem.setMealPreference(mealPreference);

        SpiceLevel spiceLevel = new SpiceLevel();
        spiceLevel.setId(spiceLevelRepository.getByName(foodItemDto.getSpiceLevel()).get());
        foodItem.setSpiceLevel(spiceLevel);

        foodItem.setAvailabilityStatus(foodItemDto.isAvailabilityStatus());
        foodItem.setName(foodItemDto.getName());
        foodItem.setSweetTooth(foodItemDto.isSweetTooth());
        foodItem.setId(foodItemDto.getId());
        return foodItem;
    }
}
