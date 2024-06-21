package org.example.Handler;

import org.example.Dto.FoodItemDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Services.RecommendationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ChefMenuHandler implements MenuHandler{
    private RecommendationService recommendationService;
    //private final String sessionToken;
    public ChefMenuHandler(){
        recommendationService = new RecommendationService();
        //this.sessionToken = sessionToken;
    }

    @Override
    public void showMenu(Scanner scanner, BufferedReader in, PrintWriter out) throws IOException {
        while(true){
            System.out.println("1. Get recommendation");
            System.out.println("2. Rollout menu");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    List<FoodItemResponseDto> foodItems = recommendationService.getRecommendation(in, out);
                    foodItems.stream().forEach(foodItemResponseDto -> foodItemResponseDto.toString());
                    break;
                case 2:

                case 3:
                    System.out.println("Exiting from Chef Menu...");
                    return;
            }
        }
    }
}
