package org.example.Handler;

import java.io.PrintWriter;

import static org.example.Constants.FormatEnum.JSON;

public class RequestHandler {
    public static void sendRequest(PrintWriter out, String command, String payload, String sessionToken) {
        String header = null;
        if(command.equals("LOGIN")){
            header = command + "|" + payload.length() + "|" + JSON;
        }
        else {
            header = command + "|" + payload.length() + "|" + JSON + "|" + sessionToken;
        }
        System.out.println("header:" + header);
        out.println(header);
        out.println(payload);
        out.flush();
    }
}
