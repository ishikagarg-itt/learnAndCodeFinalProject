package org.example.Services;

import com.google.gson.Gson;
import org.example.Dto.FoodItemDto;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FoodItemService {

    public FoodItemDto add(BufferedReader in, PrintWriter out, Scanner scanner) throws IOException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter type: ");
        String type = scanner.nextLine();
        System.out.println("Enter Availability(true/false): ");
        Boolean availability_status = scanner.nextBoolean();

        FoodItemDto foodItem = new FoodItemDto();
        foodItem.setName(name);
        foodItem.setType(type);
        foodItem.setAvailability_status(availability_status);

        Gson gson = new Gson();
        String addPayload = gson.toJson(foodItem);

        RequestHandler.sendRequest(out, "ADD_ITEM", addPayload);

        String itemResponsePayload = ResponseHandler.readResponse(in);
        FoodItemDto addedItem = gson.fromJson(itemResponsePayload, FoodItemDto.class);

        return addedItem;
    }

    public FoodItemDto update(BufferedReader in, PrintWriter out, Scanner scanner) throws IOException {
        System.out.println("Enter food item Id:");
        int id = scanner.nextInt();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Availability(true/false): ");
        Boolean availability_status = scanner.nextBoolean();

        FoodItemDto foodItem = new FoodItemDto();
        foodItem.setName(name);
        foodItem.setAvailability_status(availability_status);
        foodItem.setId(id);

        Gson gson = new Gson();
        String addPayload = gson.toJson(foodItem);

        RequestHandler.sendRequest(out, "UPDATE_ITEM", addPayload);

        String itemResponsePayload = ResponseHandler.readResponse(in);
        FoodItemDto addedItem = gson.fromJson(itemResponsePayload, FoodItemDto.class);

        return addedItem;
    }
}
