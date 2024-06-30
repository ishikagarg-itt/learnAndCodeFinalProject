package org.example.Handler;

import java.io.PrintWriter;

public class RequestHandler {
    public static void sendRequest(PrintWriter out, String command, String payload) {
        String header = command + "|" + payload.length();
        System.out.println("header:" + header);
        out.println(header);
        out.println(payload);
        out.flush();
//        if(command.equals("GET-RECOMMENDATION")){
//            out.println(header);
//            out.println(payload);
//            System.out.println("get recommendation");
////            out.println(header+"dk");
////            out.println(payload+"dk");
//        }
//        else{
//            out.println(header);
//            out.println(payload);
//        }
    }
}
