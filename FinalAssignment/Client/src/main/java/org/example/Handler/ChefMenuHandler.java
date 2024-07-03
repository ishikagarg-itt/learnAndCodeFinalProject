package org.example.Handler;

import org.example.Dto.FoodItemDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Exception.OperationFailedException;
import org.example.Services.ChefService;
import org.example.Services.RecommendationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ChefMenuHandler implements MenuHandler{
    private RecommendationService recommendationService;
    private ChefService chefService;
    private final String sessionToken;
    public ChefMenuHandler(String sessionToken){
        recommendationService = new RecommendationService();
        chefService = new ChefService();
        this.sessionToken = sessionToken;
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
            try {
                switch (choice) {
                    case 1:
                        recommendationService.getRecommendation(in, out, sessionToken);
                        break;
                    case 2:
                        String rolloutResponse = chefService.rolloutMenu(in, out, scanner, sessionToken);
                        System.out.println(rolloutResponse);
                        break;
                    case 3:
                        System.out.println("Exiting from Chef Menu...");
                        return;
                    default:
                        System.out.println("You entered a wrong option, please try again");
                        break;
                }
            }catch (OperationFailedException exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
