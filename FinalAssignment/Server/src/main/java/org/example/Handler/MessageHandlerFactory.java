package org.example.Handler;

import java.io.PrintWriter;

public class MessageHandlerFactory {
    private final LoginHandler loginHandler;
    private final RecommendationHandler recommendationHandler;
    private final FoodItemHandler foodItemHandler;
    private final ExitHandler exitHandler;

    public MessageHandlerFactory(){
        loginHandler = new LoginHandler();
        recommendationHandler = new RecommendationHandler();
        foodItemHandler = new FoodItemHandler();
        exitHandler = new ExitHandler();
    }

    public void handleMessage(String messageType, String[] headerParts, String payload, PrintWriter out) {
        switch (messageType) {
            case "LOGIN":
                loginHandler.handle(out, payload);
                break;
            case "ADD_ITEM":
                foodItemHandler.handleAdd(out, payload);
                break;
            case "UPDATE_ITEM":
                foodItemHandler.handleUpdate(out, payload);
                break;
            case "GET_ITEM":
                foodItemHandler.handleGet(out, payload);
                break;
            case "GET_ALL_ITEM":
                foodItemHandler.handleGetAll(out);
                break;
            case "DELETE_ITEM":
                foodItemHandler.handleDelete(out, payload);
                break;
            case "GET-RECOMMENDATION":
                recommendationHandler.handle(out, headerParts, payload);
                break;
            case "EXIT":
                exitHandler.handle(out);
            default:
                break;
        }
    }
}
