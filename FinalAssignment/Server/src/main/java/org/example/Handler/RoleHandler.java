package org.example.Handler;

import java.io.PrintWriter;

public interface RoleHandler {
    public void handleCommands(String messageType, String payload, PrintWriter out);
}
