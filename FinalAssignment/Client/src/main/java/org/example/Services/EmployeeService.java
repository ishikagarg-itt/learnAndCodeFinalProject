package org.example.Services;

import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.NotificationDto;
import org.example.Dto.ProfileDto;
import org.example.Dto.RatingDto;
import org.example.Handler.OutputHandler;
import org.example.Handler.ProtocolHandler;
import org.example.utils.UserInputUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.example.Constants.CommandEnum.CHOOSE_ITEMS;
import static org.example.Constants.CommandEnum.GET_ROLL_OUT_MENU;
import static org.example.Constants.CommandEnum.GIVE_DISCARD_ITEM_RATING;
import static org.example.Constants.CommandEnum.GIVE_RATING;
import static org.example.Constants.CommandEnum.UPDATE_PROFILE;
import static org.example.Constants.CommandEnum.VIEW_NOTIFICATIONS;

public class EmployeeService {

    public List<EmployeeMenuDto> getRollOutMenu(String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        protocolHandler.sendRequest(GET_ROLL_OUT_MENU.getCommandName(), "", sessionToken);
        List<EmployeeMenuDto> rolloutMenu = protocolHandler.receiveResponseList(EmployeeMenuDto.class);

        for (EmployeeMenuDto item : rolloutMenu) {
            OutputHandler.printVotedItemResponse(item);
        }
        return rolloutMenu;
    }

    public String chooseFoodItem(List<Integer> chosenItems, String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        protocolHandler.sendRequest(CHOOSE_ITEMS.getCommandName(), chosenItems, sessionToken);
        String chooseItemResponse = protocolHandler.receiveResponse( String.class);
        return chooseItemResponse;
    }

    public List<Integer> addChosenItemInList(int foodItemId, List<Integer> chosenItems) {
        chosenItems.add(foodItemId);
        return chosenItems;
    }

    public String provideRating(Scanner scanner, String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        RatingDto ratingDto = UserInputUtils.collectRatingInput(scanner);
        protocolHandler.sendRequest(GIVE_RATING.getCommandName(), ratingDto, sessionToken);
        String chooseItemResponse = protocolHandler.receiveResponse(String.class);
        return chooseItemResponse;
    }

    public List<NotificationDto> getNotifications(String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        protocolHandler.sendRequest(VIEW_NOTIFICATIONS.getCommandName(), "", sessionToken);
        List<NotificationDto> notifications = protocolHandler.receiveResponseList(NotificationDto.class);

        for (NotificationDto notification : notifications) {
            OutputHandler.printNotificationResponse(notification);
        }
        return notifications;
    }

    public String provideDiscardItemRating(Scanner scanner, String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        RatingDto ratingDto = UserInputUtils.collectDiscardItemRatingInput(scanner);
        protocolHandler.sendRequest(GIVE_DISCARD_ITEM_RATING.getCommandName(), ratingDto, sessionToken);
        String chooseItemResponse = protocolHandler.receiveResponse(String.class);
        return chooseItemResponse;
    }

    public String updateProfile(Scanner scanner, String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        ProfileDto profile = UserInputUtils.collectUpdateProfileInput(scanner);
        protocolHandler.sendRequest(UPDATE_PROFILE.getCommandName(), profile, sessionToken);
        String updateProfileResponse = protocolHandler.receiveResponse(String.class);
        return updateProfileResponse;
    }
}
