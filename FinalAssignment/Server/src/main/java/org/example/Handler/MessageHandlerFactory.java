package org.example.Handler;

import java.io.PrintWriter;

public class MessageHandlerFactory {
    private final LoginHandler loginHandler;
    private final RecommendationHandler recommendationHandler;
    private final FoodItemHandler foodItemHandler;
    private final ExitHandler exitHandler;
    private final EmployeeHandler employeeHandler;

    public MessageHandlerFactory(){
        loginHandler = new LoginHandler();
        recommendationHandler = new RecommendationHandler();
        foodItemHandler = new FoodItemHandler();
        exitHandler = new ExitHandler();
        employeeHandler = new EmployeeHandler();
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
            case "ROLL_OUT_MENU":
                recommendationHandler.handleRollOutMenu(out, headerParts, payload);
                break;
            case "GET_ROLL_OUT_MENU":
                employeeHandler.handleGetRollOutMenu(out, headerParts);
                break;
            case "CHOOSE_ITEMS":
                employeeHandler.handleChooseItems(out, headerParts, payload);
                break;
            case "GIVE_RATING":
                //recommendationHandler.handleRollOutMenu(out, headerParts, payload);
                break;
            case "EXIT":
                exitHandler.handle(out);
            default:
                break;
        }
    }
}
