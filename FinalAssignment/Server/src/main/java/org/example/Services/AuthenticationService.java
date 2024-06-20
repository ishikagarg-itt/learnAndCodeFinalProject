package org.example.Services;


import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Entity.User;
import org.example.Exception.NotFoundException;
import org.example.Repository.UserRepository;
import org.example.utils.AuthenticationUtils;

import java.util.Optional;

public class AuthenticationService {

    private UserRepository userRepository;
    public AuthenticationService(){
        userRepository = new UserRepository();
    }

    public LoginResponseDto login(LoginRequestDto loginRequest) {
        Optional<User> user = userRepository.findByName(loginRequest.getUserName());

        String sessionToken = null;

        if(user.isPresent()){
            sessionToken = AuthenticationUtils.generateSessionToken(user.get());
        }
        else{
            throw new NotFoundException("User was not found");
        }

        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setSessionToken(sessionToken);
        return  loginResponse;
    }
}
