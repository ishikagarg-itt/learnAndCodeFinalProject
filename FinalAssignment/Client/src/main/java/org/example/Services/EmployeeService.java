package org.example.Services;

import com.google.gson.Gson;
import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.RatingDto;
import org.example.Handler.OutputHandler;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {

    public List<EmployeeMenuDto> getRollOutMenu(BufferedReader in, PrintWriter out, String sessionToken) throws IOException {
        RequestHandler.sendRequest(out, "GET_ROLL_OUT_MENU", "", sessionToken);

        List<EmployeeMenuDto> rolloutMenu = ResponseHandler.readResponseList(in, EmployeeMenuDto.class);

        for (EmployeeMenuDto item : rolloutMenu) {
            OutputHandler.printVotedItemResponse(item);
        }

        return rolloutMenu;
    }

    public String chooseFoodItem(BufferedReader in, PrintWriter out, List<Integer> chosenItems, String sessionToken) throws IOException {
        Gson gson = new Gson();
        String chosenItemsPayload = gson.toJson(chosenItems);
        RequestHandler.sendRequest(out, "CHOOSE_ITEMS", chosenItemsPayload, sessionToken);

        String chooseItemResponse = ResponseHandler.readResponseObject(in, String.class);
        return chooseItemResponse;
    }

    public List<Integer> addChosenItemInList(int foodItemId, List<Integer> chosenItems) {
        chosenItems.add(foodItemId);
        return chosenItems;
    }

    public String provideRating(BufferedReader in, PrintWriter out, Scanner scanner, String sessionToken) throws IOException {
        System.out.print("Enter food item id you want to rate: ");
        int foodItemId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter rating(0-5): ");
        String rating = scanner.nextLine();
        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();

        RatingDto ratingDto = new RatingDto();
        ratingDto.setRating(rating);
        ratingDto.setComment(comment);
        ratingDto.setFoodItemId(foodItemId);

        Gson gson = new Gson();
        String ratingPayload = gson.toJson(ratingDto);
        RequestHandler.sendRequest(out, "GIVE_RATING", ratingPayload, sessionToken);

        String chooseItemResponse = ResponseHandler.readResponseObject(in, String.class);
        return chooseItemResponse;
    }
}
