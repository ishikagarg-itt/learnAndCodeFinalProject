package org.example.Handler;

import org.example.Dto.FoodItemDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Exception.OperationFailedException;
import org.example.Services.FoodItemService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AdminMenuHandler implements MenuHandler {
    private final FoodItemService foodItemService;

    private final String sessionToken;
    public AdminMenuHandler(String sessionToken){
        foodItemService = new FoodItemService();
        this.sessionToken = sessionToken;
    }
    @Override
    public void showMenu(Scanner scanner, ProtocolHandler protocolHandler) throws IOException {
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

            try {
                switch (choice) {
                    case 1:
                        String addFoodItemResponse = foodItemService.add(scanner, sessionToken, protocolHandler);
                        System.out.println(addFoodItemResponse);
                        break;
                    case 2:
                        String updateSuccessMessage = foodItemService.update(scanner, sessionToken, protocolHandler);
                        System.out.println(updateSuccessMessage);
                        break;
                    case 3:
                        String deleteSuccessMessage = foodItemService.delete(scanner, sessionToken, protocolHandler);
                        System.out.println(deleteSuccessMessage);
                        break;
                    case 4:
                        FoodItemResponseDto foodItem = foodItemService.get(scanner, sessionToken, protocolHandler);
                        OutputHandler.printFoodItemResponse(foodItem);
                        break;
                    case 5:
                        List<FoodItemResponseDto> getAllResponse = foodItemService.getAll(sessionToken, protocolHandler);
                        for (FoodItemResponseDto item : getAllResponse) {
                            OutputHandler.printFoodItemResponse(item);
                        }
                        break;
                    case 6:
                        System.out.println("Exiting from Admin Menu...");
                        return;
                }
            }catch (OperationFailedException exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
