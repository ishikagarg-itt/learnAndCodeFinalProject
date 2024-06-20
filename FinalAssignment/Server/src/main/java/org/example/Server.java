package org.example;

import org.example.Handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {

            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("Server started. Listening on port 8888...");


            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executorService.execute(clientHandler);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            executorService.shutdown();
        }
    }
}
