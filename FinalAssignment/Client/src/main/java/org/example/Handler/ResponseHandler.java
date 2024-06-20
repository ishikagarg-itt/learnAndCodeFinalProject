package org.example.Handler;

import org.example.Exception.OperationFailedException;

import java.io.BufferedReader;
import java.io.IOException;

public class ResponseHandler {

    public static String readResponse(BufferedReader in) throws IOException {
        String header = in.readLine();
        String[] headerParts = header.split("\\|");
        if(headerParts[0] == "SUCCESS"){
            int payloadLength = Integer.parseInt(headerParts[1]);
            char[] payloadBuffer = new char[payloadLength];
            in.read(payloadBuffer, 0, payloadLength);
            return new String(payloadBuffer);
        }
        else{
            throw new OperationFailedException("Operation was not completed successfully");
        }
    }
}
