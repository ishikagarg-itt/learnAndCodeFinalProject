package org.example.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Controller.EmployeeController;
import org.example.Dto.EmployeeMenuDto;
import org.example.Entity.FoodItem;
import org.example.Entity.VotedItem;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeHandler {
    private EmployeeController employeeController;

    public EmployeeHandler(){
        employeeController = new EmployeeController();
    }

    public void handleGetRollOutMenu(PrintWriter out, String[] headerParts) {
        Gson gson = new Gson();
        List<EmployeeMenuDto> foodItems = employeeController.getRollOutMenu();

        String responsePayload = gson.toJson(foodItems);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

    public void handleChooseItems(PrintWriter out, String[] headerParts, String payload) {
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
}
