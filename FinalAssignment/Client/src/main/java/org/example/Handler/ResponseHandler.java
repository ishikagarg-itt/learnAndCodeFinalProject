package org.example.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.Dto.Response;
import org.example.Exception.OperationFailedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ResponseHandler {

    private static final Gson gson = new Gson();

    public static <T> T readResponseObject(BufferedReader in, Class<T> responseType) throws IOException, OperationFailedException {
        Response response = readResponse(in);
        String[] headerParts = response.getHeaderParts();
        String payload = response.getPayload();

        if (headerParts[0].equals("SUCCESS")) {
            System.out.println("Payload " + payload);
            if (responseType == String.class) {
                return responseType.cast(payload);
            }

            try {
                return gson.fromJson(payload, responseType);
            } catch (JsonSyntaxException e) {
                throw new OperationFailedException("Failed to parse response payload");
            }
        }else if (headerParts[0].equals("ERROR")) {
            System.out.println("Error: " + payload);
            String responsePayload = gson.fromJson(payload, String.class);
            throw new OperationFailedException(responsePayload);
        } else {
            throw new OperationFailedException("Operation was not completed successfully");
        }
    }

    public static <T> List<T> readResponseList(BufferedReader in, Class<T> responseType) throws IOException, OperationFailedException {
        Response response = readResponse(in);
        String[] headerParts = response.getHeaderParts();
        String payload = response.getPayload();

        if (headerParts[0].equals("SUCCESS")) {
            Type listType = TypeToken.getParameterized(List.class, responseType).getType();
            try {
                return gson.fromJson(payload, listType);
            } catch (JsonSyntaxException e) {
                throw new OperationFailedException("Failed to parse response payload");
            }
        }else if (headerParts[0].equals("ERROR")) {
            System.out.println("Error: " + payload);
            return null;
        } else {
            throw new OperationFailedException("Operation was not completed successfully");
        }
    }

    private static Response readResponse(BufferedReader in) throws IOException, OperationFailedException {
        String[] headerParts;
        String payload;
        while (true) {
            headerParts = readHeader(in);
            System.out.println(headerParts[0]);
            payload = readPayload(headerParts, in);
            if (headerParts != null && !headerParts[0].isEmpty()) {
                break;
            }
        }
        return new Response(headerParts, payload);
    }

    private static String[] readHeader(BufferedReader in) throws IOException {
        String header = in.readLine();
        System.out.println("response header" + header);
        return header.split("\\|");
    }

    private static String readPayload(String[] headerParts, BufferedReader in) throws IOException {
        String payload = null;
        if(headerParts.length > 1) {
            System.out.println("inside read payload");
            int payloadLength = Integer.parseInt(headerParts[1]);
            char[] payloadBuffer = new char[payloadLength];
            in.read(payloadBuffer, 0, payloadLength);
            payload = new String(payloadBuffer);
        }
        return payload;
    }
}
