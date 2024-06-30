package org.example.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Controller.ChefController;
import org.example.Entity.FoodItem;

import java.io.PrintWriter;
import java.util.List;

public class ChefHandler implements RoleHandler {
    private final ChefController chefController;

    public ChefHandler() {
        chefController = new ChefController();
    }

    @Override
    public void handleCommands(String messageType, String payload, PrintWriter out) {
        switch (messageType) {
            case "GET-RECOMMENDATION":
                handle(out);
                break;
            case "ROLL_OUT_MENU":
                handleRollOutMenu(out, payload);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    private void handle(PrintWriter out) {
        Gson gson = new Gson();
        List<FoodItem> foodItems = chefController.getRecommendation();

        String responsePayload = gson.toJson(foodItems);
        System.out.println("response payload" + responsePayload);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

    private void handleRollOutMenu(PrintWriter out, String payload) {
        Gson gson = new Gson();
        List<Integer> foodItemIds = gson.fromJson(payload, new TypeToken<List<Integer>>() {}.getType());
        String rolloutMenuResponse = chefController.rolloutMenu(foodItemIds);

        String responsePayload = gson.toJson(rolloutMenuResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }
}
