package org.example.Services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Dto.FoodItemDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Handler.OutputHandler;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
        FoodItemDto addedItem = ResponseHandler.readResponse(in, FoodItemDto.class);
        return addedItem;
    }

    public String update(BufferedReader in, PrintWriter out, Scanner scanner) throws IOException {
        System.out.println("Enter food item Id:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Availability(true/false): ");
        Boolean availability_status = scanner.nextBoolean();

        FoodItemDto foodItem = new FoodItemDto();
        foodItem.setName(name);
        foodItem.setAvailability_status(availability_status);
        foodItem.setId(id);

        Gson gson = new Gson();
        String updatePayload = gson.toJson(foodItem);

        RequestHandler.sendRequest(out, "UPDATE_ITEM", updatePayload);
        String updateSuccessResponse = ResponseHandler.readResponse(in, String.class);
        return updateSuccessResponse;
    }

    public FoodItemResponseDto get(BufferedReader in, PrintWriter out, Scanner scanner) throws IOException {
        System.out.println("Enter food item Id:");
        int id = scanner.nextInt();

        Gson gson = new Gson();
        String getPayload = gson.toJson(id);

        RequestHandler.sendRequest(out, "GET_ITEM", getPayload);
        FoodItemResponseDto foodItem = ResponseHandler.readResponse(in, FoodItemResponseDto.class);
        return foodItem;
    }

    public String delete(BufferedReader in, PrintWriter out, Scanner scanner) throws IOException {
        System.out.println("Enter food item Id:");
        int id = scanner.nextInt();

        Gson gson = new Gson();
        String getPayload = gson.toJson(id);

        RequestHandler.sendRequest(out, "DELETE_ITEM", getPayload);
        String deleteSuccessMessage = ResponseHandler.readResponse(in, String.class);
        return deleteSuccessMessage;
    }

    public List<FoodItemResponseDto> getAll(BufferedReader in, PrintWriter out) throws IOException {
        RequestHandler.sendRequest(out, "GET_ALL_ITEM", "");

        List<FoodItemResponseDto> getAllResponse =ResponseHandler.readResponseList(in, FoodItemResponseDto.class);

        return getAllResponse;
    }
}
