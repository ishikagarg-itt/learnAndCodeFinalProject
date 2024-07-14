package org.example.Handler;

import org.example.Controller.ChefController;
import org.example.Controller.DiscardItemController;
import org.example.Controller.FoodItemController;
import org.example.Deserializer.RequestDeserializer;
import org.example.Dto.FoodItemDto;
import org.example.Dto.RequestData;
import org.example.Entity.DiscardItem;
import org.example.Entity.FoodItem;
import org.example.Serializer.ResponseSerializer;
import org.example.utils.SerealizationUtils;

import java.io.PrintWriter;
import java.util.List;

import static org.example.Constants.FormatEnum.JSON;

public class ChefHandler implements RoleHandler {
    private final ChefController chefController;
    private final DiscardItemController discardItemController;
    private final FoodItemController foodItemController;
    String sessionToken;

    public ChefHandler(String sessionToken) {
        chefController = new ChefController();
        discardItemController = new DiscardItemController();
        foodItemController = new FoodItemController();
        this.sessionToken = sessionToken;
    }

    @Override
    public void handleCommands(RequestData requestData, ProtocolHandler protocolHandler) {
        switch (requestData.getMessageType()) {
            case "GET_RECOMMENDATION":
                handle(requestData, protocolHandler);
                break;
            case "ROLL_OUT_MENU":
                handleRollOutMenu(requestData, protocolHandler);
                break;
            case "VIEW_DISCARD_ITEMS":
                handleViewDiscardItems(requestData, protocolHandler);
                break;
            case "DELETE_ITEM":
                handleDelete(requestData, protocolHandler);
                break;
            case "ASK_FEEDBACK":
                handleAskFeedback(requestData, protocolHandler);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    private void handle(RequestData requestData, ProtocolHandler protocolHandler) {
        List<FoodItem> foodItems = chefController.getRecommendation();
        protocolHandler.sendResponse("SUCCESS", foodItems, requestData.getFormat());
        System.out.println("Server sent response to client");
    }

    private void handleRollOutMenu(RequestData requestData, ProtocolHandler protocolHandler) {
        List<Integer> foodItemIds = protocolHandler.deserializeRequestPayloadList(requestData, Integer.class);
        String rolloutMenuResponse = chefController.rolloutMenu(foodItemIds);
        protocolHandler.sendResponse("SUCCESS", rolloutMenuResponse, requestData.getFormat());
        System.out.println("Server sent response to client");
    }

    private void handleViewDiscardItems(RequestData requestData, ProtocolHandler protocolHandler) {
        List<DiscardItem> discardItems = discardItemController.getDiscardItems();
        protocolHandler.sendResponse("SUCCESS", discardItems, requestData.getFormat());
    }

    private void handleDelete(RequestData requestData, ProtocolHandler protocolHandler) {
        int id = protocolHandler.deserializeRequestPayload(requestData, Integer.class);
        foodItemController.delete(id);
        String responsePayload = "Item deleted successfully";
        protocolHandler.sendResponse("SUCCESS", responsePayload, requestData.getFormat());
    }

    private void handleAskFeedback(RequestData requestData, ProtocolHandler protocolHandler) {
        String askFeedBackResponse = chefController.askFeedBack();
        protocolHandler.sendResponse("SUCCESS", askFeedBackResponse, requestData.getFormat());
    }
}
