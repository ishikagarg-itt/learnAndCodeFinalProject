package org.example.Services;

import org.example.Dto.FoodItemDto;
import org.example.Dto.FoodItemResponseDto;
import org.example.Handler.ProtocolHandler;
import org.example.utils.UserInputUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.example.Constants.CommandEnum.ADD_ITEM;
import static org.example.Constants.CommandEnum.DELETE_ITEM;
import static org.example.Constants.CommandEnum.GET_ALL_ITEM;
import static org.example.Constants.CommandEnum.GET_ITEM;
import static org.example.Constants.CommandEnum.UPDATE_ITEM;

public class FoodItemService {

    public String add(Scanner scanner, String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        FoodItemDto foodItem = UserInputUtils.collectFoodItemDetails(scanner);
        protocolHandler.sendRequest(ADD_ITEM.getCommandName(), foodItem, sessionToken);
        String addedItem = protocolHandler.receiveResponse(String.class);
        return addedItem;
    }

    public String update(Scanner scanner, String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        FoodItemDto foodItem = UserInputUtils.collectUpdateFoodItemDetails(scanner);
        protocolHandler.sendRequest(UPDATE_ITEM.getCommandName(), foodItem, sessionToken);
        String updateSuccessResponse = protocolHandler.receiveResponse(String.class);
        return updateSuccessResponse;
    }

    public FoodItemResponseDto get(Scanner scanner, String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        int id = UserInputUtils.collectFoodItemIdInput(scanner);
        protocolHandler.sendRequest(GET_ITEM.getCommandName(), id, sessionToken);
        FoodItemResponseDto foodItem = protocolHandler.receiveResponse(FoodItemResponseDto.class);
        return foodItem;
    }

    public String delete(Scanner scanner, String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        int id = UserInputUtils.collectFoodItemIdInput(scanner);
        protocolHandler.sendRequest(DELETE_ITEM.getCommandName(), id, sessionToken);
        String deleteSuccessMessage = protocolHandler.receiveResponse(String.class);
        return deleteSuccessMessage;
    }

    public List<FoodItemResponseDto> getAll(String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        protocolHandler.sendRequest(GET_ALL_ITEM.getCommandName(), "", sessionToken);
        List<FoodItemResponseDto> getAllResponse = protocolHandler.receiveResponseList(FoodItemResponseDto.class);
        return getAllResponse;
    }
}
