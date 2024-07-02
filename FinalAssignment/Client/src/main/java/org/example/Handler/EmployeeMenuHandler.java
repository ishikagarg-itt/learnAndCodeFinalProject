package org.example.Handler;

import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Dto.VotedItemDto;
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

    public EmployeeMenuHandler(String sessionToken){
        this.sessionToken = sessionToken;
        employeeService = new EmployeeService();
    }
    @Override
    public void showMenu(Scanner scanner, BufferedReader in, PrintWriter out) throws IOException {
        while(true){
            System.out.println("1. Choose food Item");
            System.out.println("2. Give feedback");
            System.out.println("3. View Notifications");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    employeeService.getRollOutMenu(in, out, sessionToken);
                    showChooseItemMenu(scanner, in, out);
                    break;
                case 2:
                    String ratingResponse = employeeService.provideRating(in, out, scanner, sessionToken);
                    System.out.println(ratingResponse);
                    break;
                case 3:
                    employeeService.getNotifications(in, out, sessionToken);
                    break;
                case 4:
                    System.out.println("Exiting from Chef Menu...");
                    return;
                default:
                    System.out.println("You entered a wrong option, please try again");
                    break;
            }
        }
    }

    private void showChooseItemMenu(Scanner scanner, BufferedReader in, PrintWriter out) throws IOException {
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
                    String chooseItemsResponse = employeeService.chooseFoodItem(in, out, chosenItems, sessionToken);
                    System.out.println(chooseItemsResponse);
                    isExit = true;
                    break;
                default:
                    break;
            }
        }
    }
}
