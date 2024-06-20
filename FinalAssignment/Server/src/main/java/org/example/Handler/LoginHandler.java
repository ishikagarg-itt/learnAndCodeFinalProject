package org.example.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Entity.User;
import org.example.Exception.NotFoundException;
import org.example.Repository.UserRepository;
import org.example.utils.AuthenticationUtils;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginHandler implements MessageHandler {
    private String payload;
    private UserRepository userRepository;

    public LoginHandler() {
    }

    public LoginHandler(String payload) {
        this.payload = payload;
        userRepository = new UserRepository();
    }

    @Override
    public void handle(PrintWriter out) {
        Gson gson = new Gson();
        LoginRequestDto loginData = gson.fromJson(payload, LoginRequestDto.class);

        Optional<User> user = userRepository.findByName(loginData.getUserName());

        String sessionToken = null;

        if(user.isPresent()){
            sessionToken = AuthenticationUtils.generateSessionToken(user.get());
        }
        else{
            throw new NotFoundException("User was not found");
        }

        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setSessionToken(sessionToken);
        String responsePayload = gson.toJson(loginResponse);

        String responseHeader = "SUCCESS|" + responsePayload.length();
        out.println(responseHeader);
        out.println(responsePayload);
        System.out.println("Server sent session token to client");
    }
}
