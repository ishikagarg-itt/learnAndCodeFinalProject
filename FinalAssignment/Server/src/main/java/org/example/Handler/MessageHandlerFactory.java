package org.example.Handler;

import org.example.utils.AuthenticationUtils;

import java.io.PrintWriter;

public class MessageHandlerFactory {
    private final LoginHandler loginHandler;
    private final EmployeeHandler employeeHandler;

    private String sessionToken = null;

    public MessageHandlerFactory(){
        loginHandler = new LoginHandler();
        employeeHandler = new EmployeeHandler();
    }

    public void handleMessage(String messageType, String[] headerParts, String payload, PrintWriter out) {
        if(messageType.equals("LOGIN")){
            sessionToken = loginHandler.handle(out, payload);
        }
        else{
            if (sessionToken == null) {
                throw new RuntimeException("No session token. User must log in first.");
            }
            RoleHandler roleHandler = RoleHandlerFactory.createHandler(AuthenticationUtils.getRoleFromToken(sessionToken));
            roleHandler.handleCommands(messageType, payload, out);
        }
    }
}
