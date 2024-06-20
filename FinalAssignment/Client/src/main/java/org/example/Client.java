package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Dto.CommentSentiment;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Handler.MenuHandler;
import org.example.Handler.MenuHandlerFactory;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;
import org.example.Services.AuthenticationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {

    private static AuthenticationService authenticationService;

    public void Client(){
        authenticationService = new AuthenticationService();
    }

    public static void main(String[] args) throws SocketException {
        try (Socket socket = new Socket("localhost", 8888);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            Scanner scanner = new Scanner(System.in);

            while(true) {
                showInitialMenu();
                int choice = getUserChoice(scanner);
                switch (choice) {
                    case 1:
                        String sessionToken = authenticationService.login(scanner, in, out);
                        MenuHandler menuHandler = MenuHandlerFactory.createHandler(AuthenticationService.getRoleFromToken(sessionToken));
                        if(menuHandler != null){
                            menuHandler.showMenu(scanner);
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

//            // Example list of comments
//            List<String> comments = Arrays.asList("Pizza was amazing!", "Burger was okay.", "Pasta was terrible.");
//
//            // Send comments
//            sendComments(in, out, comments);
//
//            // Send exit message to server
//            out.println("EXIT|0");
        } catch (IOException e) {
            if (e instanceof ConnectException) {
                throw new ConnectException("Server is not connected");
            }
            else if(e instanceof SocketException){
                throw new SocketException("Server got disconnected");
            }
        }
    }

//    private static void sendComments(BufferedReader in, PrintWriter out, List<String> comments) throws IOException {
//        Gson gson = new Gson();
//        String commentsPayload = gson.toJson(comments);
//
//        System.out.println("comments payload" + commentsPayload);
//
//        String commentsHeader = "COMMENT|" + commentsPayload.length() + "|" + sessionToken;
//        System.out.println("header" + commentsHeader);
//        out.println(commentsHeader);
//        out.println(commentsPayload);
//
//        // Read comments response
//        String commentsResponseHeader = in.readLine();
//        System.out.println("comments response header:" + commentsResponseHeader);
//        if (commentsResponseHeader == null) {
//            System.err.println("Received null response header");
//            return;
//        }
//
//        String[] commentsResponseHeaderParts = commentsResponseHeader.split("\\|");
//        if (commentsResponseHeaderParts.length < 1) {
//            System.err.println("Invalid response header format: " + commentsResponseHeader);
//            return;
//        }
//
//        int commentsResponsePayloadLength;
//        try {
//            commentsResponsePayloadLength = Integer.parseInt(commentsResponseHeaderParts[0]);
//        } catch (NumberFormatException e) {
//            System.err.println("Invalid payload length: " + commentsResponseHeaderParts[0]);
//            return;
//        }
//
//        char[] commentsResponsePayloadBuffer = new char[commentsResponsePayloadLength];
//        int charsRead = in.read(commentsResponsePayloadBuffer, 0, commentsResponsePayloadLength);
//        if (charsRead != commentsResponsePayloadLength) {
//            System.err.println("Failed to read the full payload. Expected: " + commentsResponsePayloadLength + ", but read: " + charsRead);
//            return;
//        }
//        String commentsResponsePayload = new String(commentsResponsePayloadBuffer);
//
//        List<CommentSentiment> sentiments = gson.fromJson(commentsResponsePayload, new TypeToken<List<CommentSentiment>>() {
//        }.getType());
//        sentiments.forEach(sentiment -> {
//            System.out.println("Comment: " + sentiment.getComment() + ", Sentiment: " + sentiment.getSentiment());
//        });
//    }

    private static void showInitialMenu() {
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter choice: ");
    }

    private static void showAuthenticatedMenu() {
        System.out.println("1. Logout");
        System.out.println("2. Perform Operation");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
    }

    private static int getUserChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume newline
        return choice;
    }
}
