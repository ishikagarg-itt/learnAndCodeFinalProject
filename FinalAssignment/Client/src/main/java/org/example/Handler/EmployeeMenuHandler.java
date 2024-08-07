package org.example.Handler;

import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Dto.VotedItemDto;
import org.example.Exception.OperationFailedException;
import org.example.Services.DiscardItemService;
import org.example.Services.EmployeeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenuHandler implements MenuHandler{

    private final String sessionToken;
    private final EmployeeService employeeService;
    private final DiscardItemService discardItemService;

    public EmployeeMenuHandler(String sessionToken){
        this.sessionToken = sessionToken;
        employeeService = new EmployeeService();
        discardItemService = new DiscardItemService();
    }
    @Override
    public void showMenu(Scanner scanner, ProtocolHandler protocolHandler) throws IOException {
        while(true){
            System.out.println("1. Choose food Item");
            System.out.println("2. Give feedback");
            System.out.println("3. View Notifications");
            System.out.println("4. Give Feedback for Discard Item");
            System.out.println("5. Update Profile");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        employeeService.getRollOutMenu(sessionToken, protocolHandler);
                        showChooseItemMenu(scanner, protocolHandler);
                        break;
                    case 2:
                        String ratingResponse = employeeService.provideRating(scanner, sessionToken, protocolHandler);
                        System.out.println(ratingResponse);
                        break;
                    case 3:
                        employeeService.getNotifications(sessionToken, protocolHandler);
                        break;
                    case 4:
                        discardItemService.getDiscardItems(sessionToken, protocolHandler);
                        String discardItemRatingResponse = employeeService.provideDiscardItemRating(scanner, sessionToken, protocolHandler);
                        System.out.println(discardItemRatingResponse);
                        break;
                    case 5:
                        String updateProfileResponse = employeeService.updateProfile(scanner, sessionToken, protocolHandler);
                        System.out.println(updateProfileResponse);
                    case 6:
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

    private void showChooseItemMenu(Scanner scanner, ProtocolHandler protocolHandler) throws IOException {
        boolean isExit = false;
        List<Integer> chosenItems = new ArrayList<>();
        while(!isExit){
            System.out.println("1. Choose food Item");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    System.out.println("Enter food item Id you want to eat:");
                    int foodItemId = scanner.nextInt();
                    chosenItems = employeeService.addChosenItemInList(foodItemId, chosenItems);
                    break;
                case 2:
                    System.out.println("chose food items: " + chosenItems);
                    String chooseItemsResponse = employeeService.chooseFoodItem(chosenItems, sessionToken, protocolHandler);
                    System.out.println(chooseItemsResponse);
                    isExit = true;
                    break;
                default:
                    break;
            }
        }
    }
}
