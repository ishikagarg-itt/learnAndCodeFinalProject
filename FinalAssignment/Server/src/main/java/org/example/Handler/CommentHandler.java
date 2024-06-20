package org.example.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Dto.CommentSentiment;
import org.example.utils.SentimentAnalysis;

import java.io.PrintWriter;
import java.util.List;

public class CommentHandler implements MessageHandler {
    private String[] headerParts;
    private String payload;

    public CommentHandler(String[] headerParts, String payload) {
        this.headerParts = headerParts;
        this.payload = payload;
    }

    @Override
    public void handle(PrintWriter out) {
        Gson gson = new Gson();
        String sessionToken = headerParts[2];
       // if (Server.isValidSessionToken(sessionToken)) {
            List<String> comments = gson.fromJson(payload, new TypeToken<List<String>>() {}.getType());

            List<CommentSentiment> sentiments = SentimentAnalysis.analyzeSentiments(comments);

            String responsePayload = gson.toJson(sentiments);
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
