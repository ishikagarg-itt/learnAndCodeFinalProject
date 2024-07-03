package org.example.Handler;

import com.google.gson.Gson;
import org.example.Controller.AuthenticationController;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Entity.User;
import org.example.Exception.NotFoundException;
import org.example.Repository.UserRepository;
import org.example.Services.AuthenticationService;
import org.example.utils.AuthenticationUtils;

import java.io.PrintWriter;
import java.util.Optional;

public class LoginHandler {
    private final Gson gson;
    private AuthenticationController authenticationController;

    public LoginHandler() {
        authenticationController = new AuthenticationController();
        this.gson = new Gson();
    }

    public String handle(PrintWriter out, String payload) {
        LoginRequestDto loginRequest = gson.fromJson(payload, LoginRequestDto.class);
        LoginResponseDto loginResponse = authenticationController.login(loginRequest);
        String responsePayload = gson.toJson(loginResponse);
        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent session token to client");
        return loginResponse.getSessionToken();

    }


}
