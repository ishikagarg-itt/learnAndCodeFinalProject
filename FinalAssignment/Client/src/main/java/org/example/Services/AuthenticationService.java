package org.example.Services;

import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Handler.ProtocolHandler;
import org.example.utils.UserInputUtils;

import java.io.IOException;
import java.util.Scanner;

import static org.example.Constants.CommandEnum.LOGIN;

public class AuthenticationService {

    public String login(Scanner scanner, ProtocolHandler protocolHandler) throws IOException {
        LoginRequestDto loginData = UserInputUtils.collectLoginInput(scanner);
        protocolHandler.sendRequest(LOGIN.getCommandName(), loginData, null);
        LoginResponseDto loginResponse = protocolHandler.receiveResponse(LoginResponseDto.class);

        if(loginResponse == null){
            return null;
        }
        String sessionToken = loginResponse.getSessionToken();
        System.out.println("Logged in with session token: " + sessionToken);
        return sessionToken;
    }

    public static String getRoleFromToken(String sessionToken){
        String[] sessionTokenParts = sessionToken.split("\\/");
        return sessionTokenParts[2];
    }
}
