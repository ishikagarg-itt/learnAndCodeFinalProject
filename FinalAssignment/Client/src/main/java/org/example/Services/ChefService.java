package org.example.Services;

import org.example.Handler.ProtocolHandler;
import org.example.utils.UserInputUtils;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static org.example.Constants.CommandEnum.ASK_FEEDBACK;
import static org.example.Constants.CommandEnum.ROLL_OUT_MENU;

public class ChefService {
    public String rolloutMenu(Scanner scanner, String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        try {
            List<Integer> foodItemIds = UserInputUtils.collectRolloutMenuInput(scanner);
            protocolHandler.sendRequest(ROLL_OUT_MENU.getCommandName(), foodItemIds, sessionToken);
            String rollOutResponse = protocolHandler.receiveResponse(String.class);
            return rollOutResponse;
        } catch (InputMismatchException exception) {
            throw new InputMismatchException("You have entered wrong input");
        }
    }

    public String askFeedback(String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        protocolHandler.sendRequest(ASK_FEEDBACK.getCommandName(), "", sessionToken);
        String askFeedbackResponse = protocolHandler.receiveResponse(String.class);
        return askFeedbackResponse;
    }
}
