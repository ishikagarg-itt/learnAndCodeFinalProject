package org.example.Handler;

import com.google.gson.Gson;
import org.example.Controller.FoodItemController;
import org.example.Dto.FoodItemDto;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Entity.FoodItem;
import org.example.utils.ConversionUtils;

import java.io.PrintWriter;
import java.util.List;

public class FoodItemHandler{
    private final FoodItemController foodItemController;
    private final Gson gson;

    public FoodItemHandler(){
        foodItemController = new FoodItemController();
        gson = new Gson();
    }

    public void handleAdd(PrintWriter out, String payload) {
        FoodItemDto foodItem = gson.fromJson(payload, FoodItemDto.class);
        FoodItem itemToBeAdded = ConversionUtils.convertFoodItemDtoToFoodItem(foodItem);
        FoodItem addedFoodItem = foodItemController.add(itemToBeAdded);
        FoodItemDto addedFoodItemDto = ConversionUtils.convertFoodItemToFoodItemDto(addedFoodItem);
        String responsePayload = gson.toJson(addedFoodItemDto);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
    }

    public void handleGet(PrintWriter out, String payload) {
        int id = gson.fromJson(payload, Integer.class);
        FoodItem foodItem = foodItemController.get(id);
        String responsePayload = gson.toJson(foodItem);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
    }

    public void handleUpdate(PrintWriter out, String payload) {
        FoodItem foodItem = gson.fromJson(payload, FoodItem.class);
        foodItemController.update(foodItem.getId(), foodItem);
        String responsePayload = "Item updated successfully";
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
    }

    public void handleGetAll(PrintWriter out) {
        List<FoodItem> foodItems = foodItemController.getAll();
        String responsePayload = gson.toJson(foodItems);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        System.out.println(responsePayload);
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent session token to client");
    }

    public void handleDelete(PrintWriter out, String payload) {
        int id = gson.fromJson(payload, Integer.class);
        foodItemController.delete(id);
        String responsePayload = "Item deleted successfully";
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
    }
}
