package org.example.Dto;

public class FoodItemDto {

    private int id;
    private String name;
    private String type;
    private boolean availability_status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailability_status() {
        return availability_status;
    }

    public void setAvailability_status(boolean availability_status) {
        this.availability_status = availability_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FoodItemDto{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", availability_status=" + availability_status +
                '}';
    }
}
