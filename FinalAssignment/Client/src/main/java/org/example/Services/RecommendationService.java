package org.example.Services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Dto.FoodItemDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Dto.LoginResponseDto;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RecommendationService {
    public List<FoodItemResponseDto> getRecommendation(BufferedReader in, PrintWriter out) throws IOException {
        Gson gson = new Gson();
        RequestHandler.sendRequest(out, "GET-RECOMMENDATION", "");

        String recommendationResponsePayload = ResponseHandler.readResponse(in);
        List<FoodItemResponseDto> recommendationResponse = gson.fromJson(recommendationResponsePayload,  new TypeToken<List<FoodItemResponseDto>>() {}.getType());
        System.out.println("Recommendation Successful");

        return recommendationResponse;
    }
}
