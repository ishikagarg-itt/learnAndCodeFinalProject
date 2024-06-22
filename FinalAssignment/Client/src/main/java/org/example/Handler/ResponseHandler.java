package org.example.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.Exception.OperationFailedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ResponseHandler {

    private static final Gson gson = new Gson();

    public static <T> T readResponse(BufferedReader in, Class<T> responseType) throws IOException, OperationFailedException {
        String[] headerParts = readHeader(in);
        System.out.println(headerParts[0]);

        if (headerParts[0].equals("SUCCESS")) {
            String payload = readPayload(headerParts, in);
            if (responseType == String.class) {
                return responseType.cast(payload);
            }

            try {
                return gson.fromJson(payload, responseType);
            } catch (JsonSyntaxException e) {
                throw new OperationFailedException("Failed to parse response payload");
            }
        } else {
            throw new OperationFailedException("Operation was not completed successfully");
        }
    }

    public static <T> List<T> readResponseList(BufferedReader in, Class<T> responseType) throws IOException, OperationFailedException {
        String[] headerParts = readHeader(in);
        System.out.println(headerParts[0]);

        if (headerParts[0].equals("SUCCESS")) {
            String payload = readPayload(headerParts, in);

            Type listType = TypeToken.getParameterized(List.class, responseType).getType();
            try {
                return gson.fromJson(payload, listType);
            } catch (JsonSyntaxException e) {
                throw new OperationFailedException("Failed to parse response payload");
            }
        } else {
            throw new OperationFailedException("Operation was not completed successfully");
        }
    }

    private static String[] readHeader(BufferedReader in) throws IOException {
        String header = in.readLine();
        return header.split("\\|");
    }

    private static String readPayload(String[] headerParts, BufferedReader in) throws IOException {
        int payloadLength = Integer.parseInt(headerParts[1]);
        char[] payloadBuffer = new char[payloadLength];
        in.read(payloadBuffer, 0, payloadLength);
        String payload = new String(payloadBuffer);
        return payload;
    }
}
