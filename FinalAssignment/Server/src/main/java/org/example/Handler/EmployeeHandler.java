package org.example.Handler;

import org.example.Controller.DiscardItemController;
import org.example.Controller.EmployeeController;
import org.example.Deserializer.RequestDeserializer;
import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.ProfileDto;
import org.example.Dto.RatingDto;
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
    public void handleCommands(String messageType, String payload, String format, PrintWriter out) {
        switch (messageType){
            case "GET_ROLL_OUT_MENU":
                handleGetRollOutMenu(out, format, AuthenticationUtils.getSession(sessionToken));
                break;
            case "CHOOSE_ITEMS":
                handleChooseItems(out, payload, format, AuthenticationUtils.getSession(sessionToken));
                break;
            case "GIVE_RATING":
                handleRating(out, payload, format, AuthenticationUtils.getSession(sessionToken));
                break;
            case "VIEW_NOTIFICATIONS":
                handleViewNotifications(out, format);
                break;
            case "VIEW_DISCARD_ITEMS":
                handleViewDiscardItems(out, format);
                break;
            case "GIVE_DISCARD_ITEM_RATING":
                handleDiscardItemRating(out, payload, format, AuthenticationUtils.getSession(sessionToken));
                break;
            case "UPDATE_PROFILE":
                handleUpdateProfile(out, payload, format, AuthenticationUtils.getSession(sessionToken));
                break;
            case "LOGOUT":
                break;
            default:
                break;
        }
    }

    private void handleGetRollOutMenu(PrintWriter out, String format, String username) {
        List<EmployeeMenuDto> foodItems = employeeController.getRollOutMenu(username);
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(foodItems);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

    private void handleChooseItems(PrintWriter out, String payload, String format, String username) {
        RequestDeserializer requestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        List<Integer> chosenFoodItemIds = requestDeserializer.deserializeList(payload, Integer.class);
        chosenFoodItemIds = chosenFoodItemIds
                .stream()
                .distinct()
                .collect(Collectors.toList());
        String chooseItemResponse = employeeController.chooseItems(chosenFoodItemIds, username);
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(chooseItemResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

    private void handleRating(PrintWriter out, String payload, String format, String username){
        RequestDeserializer requestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        RatingDto rating = requestDeserializer.deserializeObject(payload, RatingDto.class);
        String ratingResponse = employeeController.provideRating(rating, username);
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(ratingResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

    private void handleViewNotifications(PrintWriter out, String format){
        List<Notification> notifications = employeeController.viewNotifications();
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(notifications);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

    private void handleDiscardItemRating(PrintWriter out, String payload, String format, String username){
        RequestDeserializer requestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        RatingDto rating = requestDeserializer.deserializeObject(payload, RatingDto.class);
        String ratingResponse = employeeController.provideDiscardItemRating(rating, username);
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(ratingResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

    private void handleViewDiscardItems(PrintWriter out, String format) {
        List<DiscardItem> discardItems = discardItemController.getDiscardItems();
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(discardItems);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        System.out.println(responsePayload);
        out.println(responseHeader);
        out.println(responsePayload);
    }

    private void handleUpdateProfile(PrintWriter out, String payload, String format, String username) {
        RequestDeserializer requestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        ProfileDto profile = requestDeserializer.deserializeObject(payload, ProfileDto.class);
        String updateProfileResponse = employeeController.updateProfile(profile, username);
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(updateProfileResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent response to client");
    }

}
