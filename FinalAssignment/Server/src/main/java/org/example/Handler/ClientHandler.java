package org.example.Handler;

import org.example.Handler.MessageHandler;
import org.example.Handler.MessageHandlerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            while (true) {
                String header = in.readLine();
                if (header == null || header.isEmpty()) {
                    System.out.println("Connection closed by client");
                    break;
                }

                System.out.println("Header: " + header);

                String[] headerParts = header.split("\\|");

                String messageType = headerParts[0];
                System.out.println("headerParts" + headerParts[1]);
                int payloadLength = Integer.parseInt(headerParts[1]);

                char[] payloadBuffer = new char[payloadLength];
                in.read(payloadBuffer, 0, payloadLength);
                String payload = new String(payloadBuffer);
                System.out.println("Payload: " + payload);

                MessageHandler handler = MessageHandlerFactory.createHandler(messageType, headerParts, payload);
                if (handler != null) {
                    handler.handle(out);
                } else {
                    sendError(out, "Invalid message type");
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendError(PrintWriter out, String errorMessage) {
        String errorPayload = errorMessage;
        String errorHeader = "ERROR|" + errorPayload.length();
        out.println(errorHeader);
        out.println(errorPayload);
        System.out.println("Sent error to client: " + errorMessage);
    }
}
