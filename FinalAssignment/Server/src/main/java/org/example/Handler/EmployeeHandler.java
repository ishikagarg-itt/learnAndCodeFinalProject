package org.example.Handler;

import org.example.Controller.DiscardItemController;
import org.example.Controller.EmployeeController;
import org.example.Deserializer.RequestDeserializer;
import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.ProfileDto;
import org.example.Dto.RatingDto;
import org.example.Dto.RequestData;
import org.example.Entity.DiscardItem;
import org.example.Entity.Notification;
import org.example.Serializer.ResponseSerializer;
import org.example.utils.AuthenticationUtils;
import org.example.utils.SerealizationUtils;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.Constants.FormatEnum.JSON;

public class EmployeeHandler implements RoleHandler {
    private EmployeeController employeeController;
    private final DiscardItemController discardItemController;
    String sessionToken;

    public EmployeeHandler(String sessionToken){
        employeeController = new EmployeeController();
        discardItemController = new DiscardItemController();
        this.sessionToken = sessionToken;
    }

    @Override
    public void handleCommands(RequestData requestData, ProtocolHandler protocolHandler) {
        switch (requestData.getMessageType()){
            case "GET_ROLL_OUT_MENU":
                handleGetRollOutMenu(requestData, protocolHandler, AuthenticationUtils.getSession(sessionToken));
                break;
            case "CHOOSE_ITEMS":
                handleChooseItems(requestData, protocolHandler, AuthenticationUtils.getSession(sessionToken));
                break;
            case "GIVE_RATING":
                handleRating(requestData, protocolHandler, AuthenticationUtils.getSession(sessionToken));
                break;
            case "VIEW_NOTIFICATIONS":
                handleViewNotifications(requestData, protocolHandler);
                break;
            case "VIEW_DISCARD_ITEMS":
                handleViewDiscardItems(requestData, protocolHandler);
                break;
            case "GIVE_DISCARD_ITEM_RATING":
                handleDiscardItemRating(requestData, protocolHandler, AuthenticationUtils.getSession(sessionToken));
                break;
            case "UPDATE_PROFILE":
                handleUpdateProfile(requestData, protocolHandler, AuthenticationUtils.getSession(sessionToken));
                break;
            default:
                System.out.println("Invalid choice");
                protocolHandler.sendError("Invalid Choice", requestData.getFormat());
                break;
        }
    }

    private void handleGetRollOutMenu(RequestData requestData, ProtocolHandler protocolHandler, String username) {
        List<EmployeeMenuDto> foodItems = employeeController.getRollOutMenu(username);
        protocolHandler.sendResponse("SUCCESS", foodItems, requestData.getFormat());
    }

    private void handleChooseItems(RequestData requestData, ProtocolHandler protocolHandler, String username) {
        List<Integer> chosenFoodItemIds = protocolHandler.deserializeRequestPayloadList(requestData, Integer.class);
        System.out.println("chosen Food item ids: " + chosenFoodItemIds);
        chosenFoodItemIds = chosenFoodItemIds
                .stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("chosen Food item ids with remove duplicacy: " + chosenFoodItemIds);
        String chooseItemResponse = employeeController.chooseItems(chosenFoodItemIds, username);
        protocolHandler.sendResponse("SUCCESS", chooseItemResponse, requestData.getFormat());
    }

    private void handleRating(RequestData requestData, ProtocolHandler protocolHandler, String username){
        RatingDto rating = protocolHandler.deserializeRequestPayload(requestData, RatingDto.class);
        String ratingResponse = employeeController.provideRating(rating, username);
        protocolHandler.sendResponse("SUCCESS", ratingResponse, requestData.getFormat());
    }

    private void handleViewNotifications(RequestData requestData, ProtocolHandler protocolHandler){
        List<Notification> notifications = employeeController.viewNotifications();
        protocolHandler.sendResponse("SUCCESS", notifications, requestData.getFormat());
    }

    private void handleDiscardItemRating(RequestData requestData, ProtocolHandler protocolHandler, String username){
        RatingDto rating = protocolHandler.deserializeRequestPayload(requestData, RatingDto.class);
        String ratingResponse = employeeController.provideDiscardItemRating(rating, username);
        protocolHandler.sendResponse("SUCCESS", ratingResponse, requestData.getFormat());
    }

    private void handleViewDiscardItems(RequestData requestData, ProtocolHandler protocolHandler) {
        List<DiscardItem> discardItems = discardItemController.getDiscardItems();
        protocolHandler.sendResponse("SUCCESS", discardItems, requestData.getFormat());
    }

    private void handleUpdateProfile(RequestData requestData, ProtocolHandler protocolHandler, String username) {
        ProfileDto profile = protocolHandler.deserializeRequestPayload(requestData, ProfileDto.class);
        String updateProfileResponse = employeeController.updateProfile(profile, username);
        protocolHandler.sendResponse("SUCCESS", updateProfileResponse, requestData.getFormat());
    }
}
