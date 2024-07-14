package org.example.utils;

import org.example.Dto.FoodItemDto;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.ProfileDto;
import org.example.Dto.RatingDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInputUtils {
    public static List<Integer> collectRolloutMenuInput(Scanner scanner){
        System.out.print("Enter number of items: ");
        int numberOfItems = scanner.nextInt();
        List<Integer> foodItemIds = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            System.out.print("Enter food item id: ");
            int itemId = scanner.nextInt();
            foodItemIds.add(itemId);
        }
        scanner.nextLine();
        return foodItemIds;
    }

    public static LoginRequestDto collectLoginInput(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String employeeId = scanner.nextLine();
        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        LoginRequestDto loginData = new LoginRequestDto();
        loginData.setEmployeeId(employeeId);
        loginData.setUserName(userName);

        return loginData;
    }

    public static ProfileDto collectUpdateProfileInput(Scanner scanner) {
        System.out.print("Enter your meal preference(Vegetarian/Non Vegetarian/Eggetarian): ");
        String mealPreference = scanner.nextLine();
        System.out.print("Enter your spice level(High/Medium/Low): ");
        String spiceLevel = scanner.nextLine();
        System.out.println("What region food do you prefer?(North Indian/South Indian/Other)");
        String region = scanner.nextLine();
        System.out.println("Do you have a sweet tooth?(true/false)");
        Boolean sweetTooth = Boolean.parseBoolean(scanner.nextLine());

        ProfileDto profile = new ProfileDto();
        profile.setMealPreference(mealPreference);
        profile.setSpiceLevel(spiceLevel);
        profile.setRegion(region);
        profile.setSweetTooth(sweetTooth);
        return profile;
    }

    public static FoodItemDto collectFoodItemDetails(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter type: ");
        String type = scanner.nextLine();
        System.out.print("Enter Availability(true/false): ");
        Boolean availabilityStatus = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Enter your meal preference(Vegetarian/Non Vegetarian/Eggetarian): ");
        String mealPreference = scanner.nextLine();
        System.out.print("Enter your spice level(High/Medium/Low): ");
        String spiceLevel = scanner.nextLine();
        System.out.println("What region food do you prefer?(North Indian/South Indian/Other)");
        String region = scanner.nextLine();
        System.out.println("Do you have a sweet tooth?(true/false)");
        Boolean sweetTooth = Boolean.parseBoolean(scanner.nextLine());

        FoodItemDto foodItem = new FoodItemDto();
        foodItem.setName(name);
        foodItem.setType(type);
        foodItem.setAvailabilityStatus(availabilityStatus);
        foodItem.setMealPreference(mealPreference);
        foodItem.setSpiceLevel(spiceLevel);
        foodItem.setRegion(region);
        foodItem.setSweetTooth(sweetTooth);

        return foodItem;
    }

    public static FoodItemDto collectUpdateFoodItemDetails(Scanner scanner) {
        System.out.println("Enter food item Id:");
        int id = scanner.nextInt();
        scanner.nextLine();
        FoodItemDto foodItem = UserInputUtils.collectFoodItemDetails(scanner);
        foodItem.setId(id);
        return foodItem;
    }

    public static int collectFoodItemIdInput(Scanner scanner) {
        System.out.println("Enter food item Id:");
        int id = scanner.nextInt();
        return id;
    }

    public static RatingDto collectDiscardItemRatingInput(Scanner scanner) {
        RatingDto ratingDto = collectRatingInput(scanner);
        System.out.println("What didn’t you like about Food Item?");
        String feedback = scanner.nextLine();
        System.out.println("How would you like it to taste?");
        String tastePreference = scanner.nextLine();
        System.out.println("Share your recipe:");
        String recipe = scanner.nextLine();

        ratingDto.setFeedback(feedback);
        ratingDto.setTastePreference(tastePreference);
        ratingDto.setRecipe(recipe);

        return ratingDto;
    }

    public static RatingDto collectRatingInput(Scanner scanner) {
        System.out.print("Enter food item id you want to rate: ");
        int foodItemId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter rating(0-5): ");
        String rating = scanner.nextLine();
        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();

        RatingDto ratingDto = new RatingDto();
        ratingDto.setRating(rating);
        ratingDto.setComment(comment);
        ratingDto.setFoodItemId(foodItemId);
        return ratingDto;
    }
}
