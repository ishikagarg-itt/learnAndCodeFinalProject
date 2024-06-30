package org.example.Handler;

import org.example.Dto.FoodItemDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Services.FoodItemService;
import org.example.Services.RecommendationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class AdminMenuHandler implements MenuHandler {
    private final FoodItemService foodItemService;
    public AdminMenuHandler(){
        foodItemService = new FoodItemService();
    }
    @Override
    public void showMenu(Scanner scanner, BufferedReader in, PrintWriter out) throws IOException {
        while(true){
            System.out.println("1. Add Food Item");
            System.out.println("2. Update Food Item");
            System.out.println("3. Delete Food Item");
            System.out.println("4. Get Food Item");
            System.out.println("5. Get All Food Item");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    FoodItemDto addedFoodItem = foodItemService.add(in, out, scanner);
                    addedFoodItem.toString();
                    break;
                case 2:
                    String updateSuccessMessage = foodItemService.update(in, out, scanner);
                    System.out.println(updateSuccessMessage);
                    break;
                case 3:
                    String deleteSuccessMessage = foodItemService.delete(in, out, scanner);
                    System.out.println(deleteSuccessMessage);
                    break;
                case 4:
                    FoodItemResponseDto foodItem = foodItemService.get(in, out, scanner);
                    OutputHandler.printFoodItemResponse(foodItem);
                    break;
                case 5:
                    List<FoodItemResponseDto> getAllResponse = foodItemService.getAll(in, out);
                    for (FoodItemResponseDto item : getAllResponse) {
                        OutputHandler.printFoodItemResponse(item);
                    }
                    break;
                case 6:
                    System.out.println("Exiting from Admin Menu...");
                    return;
            }
        }
    }
}
