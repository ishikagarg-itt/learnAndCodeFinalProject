package org.example.Controller;

import com.google.gson.Gson;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Entity.User;
import org.example.Exception.NotFoundException;
import org.example.Repository.UserRepository;
import org.example.Services.AuthenticationService;
import org.example.utils.AuthenticationUtils;

import java.io.PrintWriter;
import java.util.Optional;

public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(){
        authenticationService = new AuthenticationService();
    }

    public LoginResponseDto login(LoginRequestDto loginRequest) {
        LoginResponseDto loginResponse = authenticationService.login(loginRequest);
        return loginResponse;
    }
}
