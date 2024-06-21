package org.example.Handler;

import org.example.Dto.FoodItemDto;
import org.example.Services.FoodItemService;
import org.example.Services.RecommendationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class AdminMenuHandler implements MenuHandler {
    private final FoodItemService foodItemService;
    //private final String sessionToken;
    public AdminMenuHandler(){
        foodItemService = new FoodItemService();
        //this.sessionToken = sessionToken;
    }
    @Override
    public void showMenu(Scanner scanner, BufferedReader in, PrintWriter out) throws IOException {
        while(true){
            System.out.println("1. Add Food Item");
            System.out.println("2. Update Food Item");
            System.out.println("3. Delete Food Item");
            System.out.println("4. Get Food Item");
            System.out.println("5. Get All Food Item");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    FoodItemDto addedFoodItem = foodItemService.add(in, out, scanner);
                    addedFoodItem.toString();
                    break;
                case 2:
                    System.out.println("Exiting from Admin Menu...");
                    return;
            }
        }
    }
}
