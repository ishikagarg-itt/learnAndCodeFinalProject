package org.example.Handler;

import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Dto.FoodItemTypeDto;
import org.example.Dto.NotificationDto;
import org.example.Dto.NotificationTypeDto;
import org.example.Dto.VotedItemDto;

import javax.management.Notification;

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

    public static void printVotedItemResponse(EmployeeMenuDto item){
        System.out.println("Id: " + item.getFoodItem().getId());
        System.out.println("Name: " + item.getFoodItem().getName());

        FoodItemTypeDto type = item.getFoodItem().getType();
        if (type != null) {
            System.out.println("Type Name: " + type.getType());
        }

        System.out.println("Rating: " + item.getAverageRating());
        System.out.println("Comment: " + item.getComment());

        System.out.println("-----");
    }

    public static void printNotificationResponse(NotificationDto notification) {
        System.out.println("Message: " + notification.getMessage());

        NotificationTypeDto type = notification.getNotificationType();
        if (type != null) {
            System.out.println("Type Name: " + type.getType());
        }

        System.out.println("-----");
    }
}
