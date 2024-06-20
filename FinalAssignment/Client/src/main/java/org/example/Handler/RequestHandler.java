package org.example.Handler;

import java.io.PrintWriter;

public class RequestHandler {
    public static void sendRequest(PrintWriter out, String command, String payload) {
        String header = command + "|" + payload.length();
        out.println(header);
        out.println(payload);
    }
}
