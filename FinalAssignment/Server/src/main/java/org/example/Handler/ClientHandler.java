package org.example.Handler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private final MessageHandlerFactory messageHandlerFactory;
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        messageHandlerFactory = new MessageHandlerFactory();
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String header;
            while (true) {
                header = in.readLine();
                System.out.println("Header: " + header);
                if (header != null && !header.isEmpty()) {
                    String[] headerParts = header.split("\\|");

                    String messageType = headerParts[0];
                    System.out.println("headerParts" + headerParts[1]);
                    int payloadLength = Integer.parseInt(headerParts[1]);

                    char[] payloadBuffer = new char[payloadLength];
                    in.read(payloadBuffer, 0, payloadLength);
                    String payload = new String(payloadBuffer);
                    System.out.println("Payload: " + payload);

                    messageHandlerFactory.handleMessage(messageType, headerParts, payload, out);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
