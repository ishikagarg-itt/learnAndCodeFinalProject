package org.example.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Controller.EmployeeController;
import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.RatingDto;
import org.example.Entity.Rating;
import org.example.utils.AuthenticationUtils;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeHandler implements RoleHandler {
    private EmployeeController employeeController;
    String sessionToken;

    public EmployeeHandler(String sessionToken){
        employeeController = new EmployeeController();
        this.sessionToken = sessionToken;
    }

    @Override
    public void handleCommands(String messageType, String payload, PrintWriter out) {
        switch (messageType){
            case "GET_ROLL_OUT_MENU":
                handleGetRollOutMenu(out);
                break;
            case "CHOOSE_ITEMS":
                handleChooseItems(out, payload);
                break;
            case "GIVE_RATING":
                handleRating(out, payload, AuthenticationUtils.getSession(sessionToken));
                break;
            case "LOGOUT":
                break;
            default:
                break;
        }
    }

    private void handleGetRollOutMenu(PrintWriter out) {
        Gson gson = new Gson();
        List<EmployeeMenuDto> foodItems = employeeController.getRollOutMenu();

        String responsePayload = gson.toJson(foodItems);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

    private void handleChooseItems(PrintWriter out, String payload) {
        Gson gson = new Gson();
        List<Integer> chosenFoodItemIds = gson.fromJson(payload, new TypeToken<List<Integer>>() {}.getType());
        chosenFoodItemIds = chosenFoodItemIds
                .stream()
                .distinct()
                .collect(Collectors.toList());
        String chooseItemResponse = employeeController.chooseItems(chosenFoodItemIds);

        String responsePayload = gson.toJson(chooseItemResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

    private void handleRating(PrintWriter out, String payload, String username){
        Gson gson = new Gson();
        RatingDto rating = gson.fromJson(payload, RatingDto.class);
        String ratingResponse = employeeController.provideRating(rating, username);

        String responsePayload = gson.toJson(ratingResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

}
