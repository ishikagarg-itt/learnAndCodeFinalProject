package org.example.Handler;

import org.example.Controller.DiscardItemController;
import org.example.Controller.FoodItemController;
import org.example.Deserializer.RequestDeserializer;
import org.example.Dto.FoodItemDto;
import org.example.Entity.DiscardItem;
import org.example.Entity.FoodItem;
import org.example.Serializer.ResponseSerializer;
import org.example.utils.ConversionUtils;
import org.example.utils.SerealizationUtils;

import java.io.PrintWriter;
import java.util.List;

import static org.example.Constants.FormatEnum.JSON;

public class AdminHandler implements RoleHandler {
    private final FoodItemController foodItemController;
    private final DiscardItemController discardItemController;
    String sessionToken;

    public AdminHandler(String sessionToken) {
        foodItemController = new FoodItemController();
        discardItemController = new DiscardItemController();
        this.sessionToken = sessionToken;
    }

    @Override
    public void handleCommands(String messageType, String payload, String format, PrintWriter out) {
        switch (messageType) {
            case "ADD_ITEM":
                handleAdd(out, payload, format);
                break;
            case "UPDATE_ITEM":
                handleUpdate(out, payload, format);
                break;
            case "GET_ITEM":
                handleGet(out, payload, format);
                break;
            case "GET_ALL_ITEM":
                handleGetAll(out, format);
                break;
            case "DELETE_ITEM":
                handleDelete(out, payload, format);
                break;
            case "VIEW_DISCARD_ITEMS":
                handleViewDiscardItems(out, format);
                break;
            case "LOGOUT":
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    private void handleAdd(PrintWriter out, String payload, String format) {
        RequestDeserializer requestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        FoodItemDto foodItem = requestDeserializer.deserializeObject(payload, FoodItemDto.class);
        FoodItem itemToBeAdded = ConversionUtils.convertFoodItemDtoToFoodItem(foodItem);
        FoodItem addedFoodItem = foodItemController.add(itemToBeAdded);
        FoodItemDto addedFoodItemDto = ConversionUtils.convertFoodItemToFoodItemDto(addedFoodItem);
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(addedFoodItemDto);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
    }

    private void handleGet(PrintWriter out, String payload, String format) {
        RequestDeserializer requestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        int id = requestDeserializer.deserializeObject(payload, Integer.class);
        FoodItem foodItem = foodItemController.get(id);
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(foodItem);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
    }

    private void handleUpdate(PrintWriter out, String payload, String format) {
        RequestDeserializer requestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        FoodItem foodItem = requestDeserializer.deserializeObject(payload, FoodItem.class);
        foodItemController.update(foodItem.getId(), foodItem);
        String responsePayload = "Item updated successfully";
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
    }

    private void handleGetAll(PrintWriter out, String format) {
        List<FoodItem> foodItems = foodItemController.getAll();
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(foodItems);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        System.out.println(responsePayload);
        out.println(responseHeader);
        out.println(responsePayload);
    }

    private void handleDelete(PrintWriter out, String payload, String format) {
        RequestDeserializer resquestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        int id = resquestDeserializer.deserializeObject(payload, Integer.class);
        foodItemController.delete(id);
        String responsePayload = "Item deleted successfully";
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
    }

    private void handleViewDiscardItems(PrintWriter out, String format) {
        List<DiscardItem> discardItems = discardItemController.getDiscardItems();
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(discardItems);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        System.out.println(responsePayload);
        out.println(responseHeader);
        out.println(responsePayload);
    }
}
