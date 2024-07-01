package org.example.Handler;

import com.google.gson.Gson;
import org.example.Controller.FoodItemController;
import org.example.Dto.FoodItemDto;
import org.example.Entity.FoodItem;
import org.example.utils.ConversionUtils;

import java.io.PrintWriter;
import java.util.List;

public class AdminHandler implements RoleHandler {
    private final FoodItemController foodItemController;
    private final Gson gson;
    String sessionToken;

    public AdminHandler(String sessionToken) {
        foodItemController = new FoodItemController();
        this.sessionToken = sessionToken;
        gson = new Gson();
    }

    @Override
    public void handleCommands(String messageType, String payload, PrintWriter out) {
        switch (messageType) {
            case "ADD_ITEM":
                handleAdd(out, payload);
                break;
            case "UPDATE_ITEM":
                handleUpdate(out, payload);
                break;
            case "GET_ITEM":
                handleGet(out, payload);
                break;
            case "GET_ALL_ITEM":
                handleGetAll(out);
                break;
            case "DELETE_ITEM":
                handleDelete(out, payload);
                break;
            case "LOGOUT":
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    private void handleAdd(PrintWriter out, String payload) {
        FoodItemDto foodItem = gson.fromJson(payload, FoodItemDto.class);
        FoodItem itemToBeAdded = ConversionUtils.convertFoodItemDtoToFoodItem(foodItem);
        FoodItem addedFoodItem = foodItemController.add(itemToBeAdded);
        FoodItemDto addedFoodItemDto = ConversionUtils.convertFoodItemToFoodItemDto(addedFoodItem);
        String responsePayload = gson.toJson(addedFoodItemDto);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
    }

    private void handleGet(PrintWriter out, String payload) {
        int id = gson.fromJson(payload, Integer.class);
        FoodItem foodItem = foodItemController.get(id);
        String responsePayload = gson.toJson(foodItem);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
    }

    private void handleUpdate(PrintWriter out, String payload) {
        FoodItem foodItem = gson.fromJson(payload, FoodItem.class);
        foodItemController.update(foodItem.getId(), foodItem);
        String responsePayload = "Item updated successfully";
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
    }

    private void handleGetAll(PrintWriter out) {
        List<FoodItem> foodItems = foodItemController.getAll();
        String responsePayload = gson.toJson(foodItems);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        System.out.println(responsePayload);
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent session token to client");
    }

    private void handleDelete(PrintWriter out, String payload) {
        int id = gson.fromJson(payload, Integer.class);
        foodItemController.delete(id);
        String responsePayload = "Item deleted successfully";
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
    }
}
