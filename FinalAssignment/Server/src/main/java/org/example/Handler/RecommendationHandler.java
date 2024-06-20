package org.example.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Controller.ChefController;
import org.example.Dto.CommentSentiment;
import org.example.utils.AuthenticationUtils;
import org.example.utils.SentimentAnalysis;

import java.io.PrintWriter;
import java.util.List;

public class RecommendationHandler{

    private final ChefController chefController;
    public RecommendationHandler() {
        chefController = new ChefController();
    }

    public void handle(PrintWriter out, String[] headerParts, String payload) {
        Gson gson = new Gson();
        //String sessionToken = headerParts[2];
        //if (AuthenticationUtils.isValidSessionToken(sessionToken)) {
            //List<String> comments = gson.fromJson(payload, new TypeToken<List<String>>() {}.getType());
            String message = chefController.getRecommendation();

            String responsePayload = gson.toJson(message);
            String responseHeader = "RESPONSE|" + responsePayload.length();
            out.println(responseHeader);
            out.println(responsePayload);
            System.out.println("Server sent response to client");
//        } else {
//            String errorPayload = "Invalid session token";
//            String errorHeader = "ERROR|" + errorPayload.length();
//            out.println(errorHeader);
//            out.println(errorPayload);
//            System.out.println("Sent error to client: Invalid session token");
//        }
    }
}
