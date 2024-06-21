package org.example;
import org.example.Handler.ClientHandler;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        //ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            ServerSocket serverSocket = new ServerSocket();
            System.out.println("Server started. Listening on port 8000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}