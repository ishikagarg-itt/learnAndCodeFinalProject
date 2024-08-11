package org.example.Handler;

import org.example.Constants.ResponseCodeEnum;
import org.example.Controller.DiscardItemController;
import org.example.Controller.FoodItemController;
import org.example.Deserializer.RequestDeserializer;
import org.example.Dto.FoodItemDto;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.RequestData;
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
    public void handleCommands(RequestData requestData, ProtocolHandler protocolHandler) {
        switch (requestData.getMessageType().toUpperCase()) {
            case "ADD_ITEM":
                handleAdd(requestData, protocolHandler);
                break;
            case "UPDATE_ITEM":
                handleUpdate(requestData, protocolHandler);
                break;
            case "GET_ITEM":
                handleGet(requestData, protocolHandler);
                break;
            case "GET_ALL_ITEM":
                handleGetAll(requestData, protocolHandler);
                break;
            case "DELETE_ITEM":
                handleDelete(requestData, protocolHandler);
                break;
            case "VIEW_DISCARD_ITEMS":
                handleViewDiscardItems(requestData, protocolHandler);
                break;
            default:
                System.out.println("Invalid choice");
                protocolHandler.sendError("Invalid Choice", requestData.getFormat());
                break;
        }
    }

    private void handleAdd(RequestData requestData, ProtocolHandler protocolHandler) {
        FoodItemDto foodItem = protocolHandler.deserializeRequestPayload(requestData, FoodItemDto.class);
        FoodItem itemToBeAdded = ConversionUtils.convertFoodItemDtoToFoodItem(foodItem);
        String addedFoodItemResponse = foodItemController.add(itemToBeAdded);
        protocolHandler.sendResponse(ResponseCodeEnum.SUCCESS.toString(), addedFoodItemResponse, requestData.getFormat());
    }

    private void handleGet(RequestData requestData, ProtocolHandler protocolHandler) {
        int id = protocolHandler.deserializeRequestPayload(requestData, Integer.class);
        FoodItem foodItem = foodItemController.get(id);
        protocolHandler.sendResponse(ResponseCodeEnum.SUCCESS.toString(), foodItem, requestData.getFormat());
    }

    private void handleUpdate(RequestData requestData, ProtocolHandler protocolHandler) {
        FoodItemDto foodItem = protocolHandler.deserializeRequestPayload(requestData, FoodItemDto.class);
        FoodItem itemToBeUpdated = ConversionUtils.convertFoodItemDtoToFoodItem(foodItem);
        foodItemController.update(itemToBeUpdated.getId(), itemToBeUpdated);
        String responsePayload = "Item updated successfully";
        protocolHandler.sendResponse(ResponseCodeEnum.SUCCESS.toString(), responsePayload, requestData.getFormat());
    }

    private void handleGetAll(RequestData requestData, ProtocolHandler protocolHandler) {
        List<FoodItem> foodItems = foodItemController.getAll();
        protocolHandler.sendResponse(ResponseCodeEnum.SUCCESS.toString(), foodItems, requestData.getFormat());
    }

    private void handleDelete(RequestData requestData, ProtocolHandler protocolHandler) {
        int id = protocolHandler.deserializeRequestPayload(requestData, Integer.class);
        foodItemController.delete(id);
        String responsePayload = "Item deleted successfully";
        protocolHandler.sendResponse(ResponseCodeEnum.SUCCESS.toString(), responsePayload, requestData.getFormat());
    }

    private void handleViewDiscardItems(RequestData requestData, ProtocolHandler protocolHandler) {
        List<DiscardItem> discardItems = discardItemController.getDiscardItems();
        protocolHandler.sendResponse(ResponseCodeEnum.SUCCESS.toString(), discardItems, requestData.getFormat());
    }
}
