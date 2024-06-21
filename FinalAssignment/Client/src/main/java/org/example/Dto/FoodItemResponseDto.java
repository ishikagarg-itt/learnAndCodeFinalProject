package org.example.Dto;

public class FoodItemResponseDto {
    private int id;
    private String name;
    private FoodItemTypeDto type;
    private boolean availabilityStatus;

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
}
