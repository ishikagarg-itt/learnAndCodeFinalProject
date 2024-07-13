package org.example.Handler;

import org.example.Dto.FoodItemDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Exception.OperationFailedException;
import org.example.Services.ChefService;
import org.example.Services.DiscardItemService;
import org.example.Services.FoodItemService;
import org.example.Services.RecommendationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChefMenuHandler implements MenuHandler{
    private RecommendationService recommendationService;
    private ChefService chefService;
    private FoodItemService foodItemService;
    private DiscardItemService discardItemService;
    private final String sessionToken;
    public ChefMenuHandler(String sessionToken){
        recommendationService = new RecommendationService();
        chefService = new ChefService();
        foodItemService = new FoodItemService();
        discardItemService = new DiscardItemService();
        this.sessionToken = sessionToken;
    }

    @Override
    public void showMenu(Scanner scanner, BufferedReader in, PrintWriter out) throws IOException {
        while(true){
            System.out.println("1. Get recommendation");
            System.out.println("2. Rollout menu");
            System.out.println("3. View Discard Items");
            System.out.println("4. Logout");
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
                        discardItemService.getDiscardItems(in, out, sessionToken);
                        showDiscardItemMenu(scanner, in, out);
                        break;
                    case 4:
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

    private void showDiscardItemMenu(Scanner scanner, BufferedReader in, PrintWriter out) throws IOException {
        boolean isExit = false;
        while(!isExit){
            System.out.println("1. Delete item from menu");
            System.out.println("2. Ask for feedback");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    String deleteSuccessMessage = foodItemService.delete(in, out, scanner, sessionToken);
                    System.out.println(deleteSuccessMessage);
                    break;
                case 2:
                    String askFeedbackResponse = chefService.askFeedback(in, out, sessionToken);
                    System.out.println(askFeedbackResponse);
                    break;
                case 3:
                    isExit = true;
                    break;
                default:
                    break;
            }
        }
    }
}
