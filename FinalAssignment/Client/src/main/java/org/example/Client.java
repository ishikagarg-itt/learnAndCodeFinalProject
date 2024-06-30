package org.example;

import org.example.Handler.MenuHandler;
import org.example.Handler.MenuHandlerFactory;
import org.example.Services.AuthenticationService;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static AuthenticationService authenticationService;

    public Client(){
        authenticationService = new AuthenticationService();
    }

    public static void main(String[] args) {
        new Client();
        try {
            Socket socket = new Socket("localhost", 8000);
            System.out.println("Connected to server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                MenuHandlerFactory.showInitialMenu();
                int choice = getUserChoice(scanner);
                switch (choice) {
                    case 1:
                        String sessionToken = authenticationService.login(scanner, in, out);
                        MenuHandler menuHandler = MenuHandlerFactory.createHandler(authenticationService.getRoleFromToken(sessionToken), sessionToken);
                        if(menuHandler != null){
                            menuHandler.showMenu(scanner, in, out);
                        }
                        else {
                            System.out.println("Invalid role type");
                        }
                        break;
                    case 2:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getUserChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
}