package org.example.Handler;

import org.example.Handler.MessageHandler;

import java.io.PrintWriter;

public class ExitHandler implements MessageHandler {
    @Override
    public void handle(PrintWriter out) {
        System.out.println("Closing connection...");
    }
}
