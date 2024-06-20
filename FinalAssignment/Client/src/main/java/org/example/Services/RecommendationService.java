package org.example.Services;

import org.example.Dto.FoodItemDto;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

public class RecommendationService {
    public List<FoodItemDto> getRecommendation(BufferedReader in, PrintWriter out){
        RequestHandler.sendRequest(out, "LOGIN", loginPayload);

        String loginResponsePayload = ResponseHandler.readResponse(in);
        return null;
    }
}
