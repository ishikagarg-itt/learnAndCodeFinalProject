package org.example.Handler;

import org.example.Dto.FoodItemResponseDto;
import org.example.Dto.FoodItemTypeDto;

public class OutputHandler {
    public static void printFoodItemResponse(FoodItemResponseDto item){
        System.out.println("Food Item ID: " + item.getId());
        System.out.println("Name: " + item.getName());
        System.out.println("Availability Status: " + item.isAvailabilityStatus());

        FoodItemTypeDto type = item.getType();
        if (type != null) {
            System.out.println("Type Name: " + type.getType());
        }

        System.out.println("-----");
    }
}
