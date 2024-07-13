package org.example.Handler;

import org.example.Controller.ChefController;
import org.example.Controller.DiscardItemController;
import org.example.Controller.FoodItemController;
import org.example.Deserializer.RequestDeserializer;
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
    public void handleCommands(String messageType, String payload, String format, PrintWriter out) {
        switch (messageType) {
            case "GET-RECOMMENDATION":
                handle(out, format);
                break;
            case "ROLL_OUT_MENU":
                handleRollOutMenu(out, payload, format);
                break;
            case "VIEW_DISCARD_ITEMS":
                handleViewDiscardItems(out, format);
                break;
            case "DELETE_ITEM":
                handleDelete(out, payload, format);
                break;
            case "ASK_FEEDBACK":
                handleAskFeedback(out, format);
                break;
            case "LOGOUT":
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    private void handle(PrintWriter out, String format) {
        List<FoodItem> foodItems = chefController.getRecommendation();
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(foodItems);
        System.out.println("response payload" + responsePayload);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

    private void handleRollOutMenu(PrintWriter out, String payload, String format) {
        RequestDeserializer requestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        List<Integer> foodItemIds = requestDeserializer.deserializeList(payload, Integer.class);
        String rolloutMenuResponse = chefController.rolloutMenu(foodItemIds);
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(rolloutMenuResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
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

    private void handleDelete(PrintWriter out, String payload, String format) {
        RequestDeserializer requestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        int id = requestDeserializer.deserializeObject(payload, Integer.class);
        foodItemController.delete(id);
        String responsePayload = "Item deleted successfully";
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
    }

    private void handleAskFeedback(PrintWriter out, String format) {
        String askFeedBackResponse = chefController.askFeedBack();
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(askFeedBackResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        System.out.println(responsePayload);
        out.println(responseHeader);
        out.println(responsePayload);
    }
}
