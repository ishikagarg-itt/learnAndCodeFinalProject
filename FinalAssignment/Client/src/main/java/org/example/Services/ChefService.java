package org.example.Services;

import com.google.gson.Gson;
import org.example.Dto.FoodItemDto;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ChefService {
    public String rolloutMenu(BufferedReader in, PrintWriter out, Scanner scanner) throws IOException {
        try {
            System.out.print("Enter number of items: ");
            int numberOfItems = scanner.nextInt();

            List<Integer> foodItemIds = new ArrayList<>();
            for (int i = 0; i < numberOfItems; i++) {
                System.out.print("Enter food item id: ");
                int itemId = scanner.nextInt();
                foodItemIds.add(itemId);
            }

            Gson gson = new Gson();
            String addPayload = gson.toJson(foodItemIds);

            RequestHandler.sendRequest(out, "ROLL_OUT_MENU", addPayload);
            String rollOutResponse = ResponseHandler.readResponse(in, String.class);
            return rollOutResponse;
        }catch(InputMismatchException exception){
            throw new InputMismatchException("You have entered wrong input");
        }
    }
}
