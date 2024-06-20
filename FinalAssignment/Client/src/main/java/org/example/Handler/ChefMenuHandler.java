package org.example.Handler;

import org.example.Dto.FoodItemDto;
import org.example.Services.RecommendationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ChefMenuHandler implements MenuHandler{
    private RecommendationService recommendationService;
    private final String sessionToken;
    public ChefMenuHandler(String sessionToken){
        recommendationService = new RecommendationService();
        this.sessionToken = sessionToken;
    }

    @Override
    public void showMenu(Scanner scanner, BufferedReader in, PrintWriter out) throws IOException {
        System.out.println("1. Get recommendation");
        System.out.println("2. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        while(true){
            switch (choice){
                case 1:
                    String foodItems = recommendationService.getRecommendation(in, out, sessionToken);
                    System.out.println(foodItems);
                case 2:
                    System.out.println("Exiting from Chef Menu...");
                    return;
            }
        }
    }
}
