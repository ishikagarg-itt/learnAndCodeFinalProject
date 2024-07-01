package org.example.Handler;

import org.example.utils.AuthenticationUtils;

import java.io.PrintWriter;

public class MessageHandlerFactory {
    private final LoginHandler loginHandler;

    private String sessionToken = null;

    public MessageHandlerFactory(){
        loginHandler = new LoginHandler();
    }

    public void handleMessage(String messageType, String payload, PrintWriter out) {
        if(messageType.equals("LOGIN")){
            sessionToken = loginHandler.handle(out, payload);
        }
        else{
            if (sessionToken == null) {
                throw new RuntimeException("No session token. User must log in first.");
            }
            RoleHandler roleHandler = RoleHandlerFactory.createHandler(AuthenticationUtils.getRoleFromToken(sessionToken), sessionToken);
            roleHandler.handleCommands(messageType, payload, out);
        }
    }
}
