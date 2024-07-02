package org.example.Services;

import com.google.gson.Gson;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AuthenticationService {

    public String login(Scanner scanner, BufferedReader in, PrintWriter out) throws IOException {
        System.out.print("Enter employee ID: ");
        String employeeId = scanner.nextLine();
        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        LoginRequestDto loginData = new LoginRequestDto();
        loginData.setEmployeeId(employeeId);
        loginData.setUserName(userName);

        Gson gson = new Gson();
        String loginPayload = gson.toJson(loginData);

        RequestHandler.sendRequest(out, "LOGIN", loginPayload, null);
        LoginResponseDto loginResponse = ResponseHandler.readResponseObject(in, LoginResponseDto.class);
        String sessionToken = loginResponse.getSessionToken();
        System.out.println("Logged in, session token: " + sessionToken);
        return sessionToken;
    }

    public static String getRoleFromToken(String sessionToken){
        String[] sessionTokenParts = sessionToken.split("\\/");
        return sessionTokenParts[2];
    }
}
