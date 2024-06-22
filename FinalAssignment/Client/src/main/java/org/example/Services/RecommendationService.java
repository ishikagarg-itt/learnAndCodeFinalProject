package org.example.Services;

import org.example.Dto.FoodItemResponseDto;
import org.example.Handler.OutputHandler;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RecommendationService {
    public List<FoodItemResponseDto> getRecommendation(BufferedReader in, PrintWriter out) throws IOException {
        RequestHandler.sendRequest(out, "GET-RECOMMENDATION", "");

        List<FoodItemResponseDto> recommendationResponse = ResponseHandler.readResponseList(in, FoodItemResponseDto.class);

        for (FoodItemResponseDto item : recommendationResponse) {
            OutputHandler.printFoodItemResponse(item);
        }
        System.out.println("Recommendation Successful");

        return recommendationResponse;
    }
}
