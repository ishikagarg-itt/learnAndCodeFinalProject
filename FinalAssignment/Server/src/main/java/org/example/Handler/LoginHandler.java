package org.example.Handler;

import com.google.gson.Gson;
import org.example.Constants.ResponseCodeEnum;
import org.example.Controller.AuthenticationController;
import org.example.Deserializer.RequestDeserializer;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Dto.RequestData;
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

    public String handle(RequestData requestData, ProtocolHandler protocolHandler) {
        LoginRequestDto loginRequest = protocolHandler.deserializeRequestPayload(requestData, LoginRequestDto.class);
        LoginResponseDto loginResponse = authenticationController.login(loginRequest);
        protocolHandler.sendResponse(ResponseCodeEnum.SUCCESS.toString(), loginResponse, requestData.getFormat());
        System.out.println("Server sent session token to client");
        return loginResponse.getSessionToken();

    }


}
