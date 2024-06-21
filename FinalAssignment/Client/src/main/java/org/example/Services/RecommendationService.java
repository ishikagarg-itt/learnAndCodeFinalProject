package org.example.Services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Dto.FoodItemDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Dto.FoodItemTypeDto;
import org.example.Dto.LoginResponseDto;
import org.example.Handler.OutputHandler;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;
import org.example.Storage.MenuStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RecommendationService {
    public List<FoodItemResponseDto> getRecommendation(BufferedReader in, PrintWriter out) throws IOException {
        MenuStorage menuStorage = MenuStorage.getInstance();
        Gson gson = new Gson();
        RequestHandler.sendRequest(out, "GET-RECOMMENDATION", "");

        String recommendationResponsePayload = ResponseHandler.readResponse(in);
        System.out.println("Payload: " + recommendationResponsePayload);
        List<FoodItemResponseDto> recommendationResponse = gson.fromJson(recommendationResponsePayload,  new TypeToken<List<FoodItemResponseDto>>() {}.getType());

        menuStorage.setMenu(recommendationResponse);

        for (FoodItemResponseDto item : recommendationResponse) {
            OutputHandler.printFoodItemResponse(item);
        }
        System.out.println("Recommendation Successful");

        return recommendationResponse;
    }
}
