package org.example.Handler;

import com.google.gson.Gson;
import org.example.Controller.AuthenticationController;
import org.example.Deserializer.RequestDeserializer;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Serializer.ResponseSerializer;
import org.example.utils.SerealizationUtils;

import java.io.PrintWriter;

import static org.example.Constants.FormatEnum.JSON;

public class LoginHandler {
    private final Gson gson;
    private AuthenticationController authenticationController;

    public LoginHandler() {
        authenticationController = new AuthenticationController();
        this.gson = new Gson();
    }

    public String handle(PrintWriter out, String payload, String format) {
        RequestDeserializer resquestDeserializer = SerealizationUtils.getRequestDeserializer(format);
        LoginRequestDto loginRequest = resquestDeserializer.deserializeObject(payload, LoginRequestDto.class);
        LoginResponseDto loginResponse = authenticationController.login(loginRequest);
        ResponseSerializer responseSerializer = SerealizationUtils.getResponseSerializer(format);
        String responsePayload = responseSerializer.serialize(loginResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length() + "|" + JSON;
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent session token to client");
        return loginResponse.getSessionToken();

    }


}
