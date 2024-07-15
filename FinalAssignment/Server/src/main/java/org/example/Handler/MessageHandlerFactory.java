package org.example.Handler;

import com.google.gson.Gson;
import org.example.Dto.LoginRequestDto;
import org.example.Dto.RequestData;
import org.example.Exception.NotFoundException;
import org.example.Exception.OperationFailedException;
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

    public void handleMessage(RequestData requestData, ProtocolHandler protocolHandler) {
        try {
            if (requestData.getMessageType().equals("LOGIN")) {
                sessionToken = loginHandler.handle(requestData, protocolHandler);
            } else {
                if (sessionToken == null) {
                    throw new RuntimeException("No session token. User must log in first.");
                }
                RoleHandler roleHandler = RoleHandlerFactory.createHandler(AuthenticationUtils.getRoleFromToken(sessionToken), sessionToken);
                roleHandler.handleCommands(requestData, protocolHandler);
            }
        }catch(NotFoundException | IllegalStateException | OperationFailedException e) {
            protocolHandler.sendError(e.getMessage(), requestData.getFormat());
        }catch (RuntimeException e){
            protocolHandler.sendError(e.getMessage(), requestData.getFormat());
        }
    }
}
