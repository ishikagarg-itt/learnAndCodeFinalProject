package org.example.Handler;

import com.google.gson.Gson;
import org.example.Exception.NotFoundException;
import org.example.utils.AuthenticationUtils;
import org.springframework.dao.DataAccessException;

import java.io.PrintWriter;

public class MessageHandlerFactory {
    private final LoginHandler loginHandler;
    private final Gson gson = new Gson();

    private String sessionToken = null;

    public MessageHandlerFactory(){
        loginHandler = new LoginHandler();
    }

    public void handleMessage(String messageType, String payload, PrintWriter out) {
        try {
            if (messageType.equals("LOGIN")) {
                sessionToken = loginHandler.handle(out, payload);
            } else {
                if (sessionToken == null) {
                    throw new RuntimeException("No session token. User must log in first.");
                }
                RoleHandler roleHandler = RoleHandlerFactory.createHandler(AuthenticationUtils.getRoleFromToken(sessionToken), sessionToken);
                roleHandler.handleCommands(messageType, payload, out);
            }
        }catch(NotFoundException e) {
            sendError(out, e.getMessage());
        }catch(IllegalStateException e){
            sendError(out, e.getMessage());
        }catch (RuntimeException e){
            sendError(out, e.getMessage());
        }
    }

    private void sendError(PrintWriter out, String errorMessage) {
        String errorResponse = errorMessage;
        String errorPayload = gson.toJson(errorResponse);
        String errorHeader = "ERROR|" + errorPayload.length();
        out.println(errorHeader);
        out.println(errorPayload);
        System.out.println("Sent error to client: " + errorMessage);
    }
}
