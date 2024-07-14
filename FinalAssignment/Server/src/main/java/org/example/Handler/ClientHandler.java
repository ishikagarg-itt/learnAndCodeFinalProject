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
            ProtocolHandler protocolHandler = new ProtocolHandler(out, in);
            while (true) {
                String header = in.readLine();
                System.out.println("Header: " + header);
                if (header != null && !header.isEmpty()) {
                    RequestData requestData = protocolHandler.receiveRequest(header);
                    if (!isValidSession(requestData)) {
                        protocolHandler.sendError("Invalid or missing session token", requestData.getFormat());
                        continue;
                    }
                    messageHandlerFactory.handleMessage(requestData, protocolHandler);
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
