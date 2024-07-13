package org.example.Handler;
import com.google.gson.Gson;
import org.example.Dto.RequestData;
import org.example.Exception.NotFoundException;
import org.example.utils.AuthenticationUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private final MessageHandlerFactory messageHandlerFactory;

    private final Gson gson = new Gson();
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        messageHandlerFactory = new MessageHandlerFactory();
    }

    public void run() {
        PrintWriter out = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            while (true) {
                String header = in.readLine();
                System.out.println("Header: " + header);
                if (header != null && !header.isEmpty()) {
                    RequestData requestData = readRequest(header, in);
                    if (!isValidSession(requestData)) {
                        System.out.println("Invalid or missing session token.");
                        //sendError(out, "Invalid or missing session token.\"");
                        continue;
                    }
                    messageHandlerFactory.handleMessage(requestData.getMessageType(), requestData.getPayload(), requestData.getFormat(), out);
                }
            }
        } catch (IOException e) {
            if(e instanceof SocketException){
                closeClientSocket();
            }
        }finally{
           closeClientSocket();
        }
    }

    private RequestData readRequest(String header, BufferedReader in) throws IOException {
        String[] headerParts = header.split("\\|");

        String messageType = headerParts[0];
        int payloadLength = Integer.parseInt(headerParts[1]);
        String payload = readPayload(in, payloadLength);
        String format = headerParts[2];
        String sessionToken = headerParts.length > 3 ? headerParts[3] : null;

        return new RequestData(messageType, payloadLength, payload, format, sessionToken);
    }

    private String readPayload(BufferedReader in, int payloadLength) throws IOException {
        char[] payloadBuffer = new char[payloadLength];
        in.read(payloadBuffer, 0, payloadLength);
        return new String(payloadBuffer);
    }

    private void closeClientSocket() {
        if (clientSocket != null && !clientSocket.isClosed()) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidSession(RequestData requestData){
        return "LOGIN".equals(requestData.getMessageType()) || (requestData.getSessionToken() != null && AuthenticationUtils.isValidSessionToken(requestData.getSessionToken()));
    }


}
