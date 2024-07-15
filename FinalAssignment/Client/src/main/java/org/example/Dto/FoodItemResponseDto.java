package org.example.Dto;

public class FoodItemResponseDto {
    private int id;
    private String name;
    private FoodItemTypeDto type;

    private MealPreferenceDto mealPreference;
    private SpiceLevelDto spiceLevel;
    private RegionDto region;
    private boolean availabilityStatus;
    private boolean sweetTooth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodItemTypeDto getType() {
        return type;
    }

    public void setType(FoodItemTypeDto type) {
        this.type = type;
    }

    public boolean isAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public MealPreferenceDto getMealPreference() {
        return mealPreference;
    }

    public void setMealPreference(MealPreferenceDto mealPreference) {
        this.mealPreference = mealPreference;
    }

    public SpiceLevelDto getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(SpiceLevelDto spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public RegionDto getRegion() {
        return region;
    }

    public void setRegion(RegionDto region) {
        this.region = region;
    }

    public boolean isSweetTooth() {
        return sweetTooth;
    }

    public void setSweetTooth(boolean sweetTooth) {
        this.sweetTooth = sweetTooth;
    }
}
