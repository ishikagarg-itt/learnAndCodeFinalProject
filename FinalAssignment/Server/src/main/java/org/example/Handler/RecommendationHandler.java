package org.example.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Controller.ChefController;
import org.example.Dto.CommentSentiment;
import org.example.Entity.FoodItem;
import org.example.utils.AuthenticationUtils;
import org.example.utils.SentimentAnalysis;

import java.io.PrintWriter;
import java.util.List;

public class RecommendationHandler {

    private final ChefController chefController;

    public RecommendationHandler() {
        chefController = new ChefController();
    }

    public void handle(PrintWriter out, String[] headerParts, String payload) {
        Gson gson = new Gson();
        List<FoodItem> foodItems = chefController.getRecommendation();

        String responsePayload = gson.toJson(foodItems);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }
}
