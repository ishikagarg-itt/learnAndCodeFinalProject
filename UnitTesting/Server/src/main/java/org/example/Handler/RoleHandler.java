package org.example.Handler;

import org.example.Dto.RequestData;

import java.io.PrintWriter;

public interface RoleHandler {
    public void handleCommands(RequestData requestData, ProtocolHandler protocolHandler);
}
