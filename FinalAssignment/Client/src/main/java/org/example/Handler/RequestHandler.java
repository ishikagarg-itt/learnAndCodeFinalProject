package org.example.Handler;

import java.io.PrintWriter;

public class RequestHandler {
    public static void sendRequest(PrintWriter out, String command, String payload, String sessionToken) {
        String header = null;
        if(command.equals("LOGIN")){
            header = command + "|" + payload.length();
        }
        else {
            header = command + "|" + payload.length() + "|" + sessionToken;
        }
        System.out.println("header:" + header);
        out.println(header);
        out.println(payload);
        out.flush();
    }
}
