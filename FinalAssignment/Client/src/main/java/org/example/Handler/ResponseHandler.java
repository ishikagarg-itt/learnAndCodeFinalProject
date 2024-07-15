package org.example.Handler;

import org.example.Constants.ResponseCodeEnum;
import org.example.Deserializer.ResponseDeserializer;
import org.example.Deserializer.ResponseDeserializerFactory;
import org.example.Dto.Response;
import org.example.Exception.OperationFailedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class ResponseHandler {
    public static <T> T readResponseObject(BufferedReader in, Class<T> responseType) throws IOException, OperationFailedException {
        Response response = readResponse(in);
        String[] headerParts = response.getHeaderParts();
        String payload = response.getPayload();
        String format = response.getFormat();

        if (headerParts[0].equals("SUCCESS")) {
            System.out.println("Payload " + payload);
            return deserializeResponseObject(payload, responseType, format);
        } else if (headerParts[0].equals("ERROR")) {
            String responsePayload = deserializeResponseObject(payload, String.class, format);
            throw new OperationFailedException(responsePayload);
        } else {
            throw new OperationFailedException("Operation was not completed successfully");
        }
    }

    public static <T> List<T> readResponseList(BufferedReader in, Class<T> responseType) throws IOException, OperationFailedException {
        Response response = readResponse(in);
        String[] headerParts = response.getHeaderParts();
        String payload = response.getPayload();
        String format = response.getFormat();

        if (headerParts[0].equals(ResponseCodeEnum.SUCCESS.toString())) {
            return deserializeResponseList(payload, responseType, format);
        } else if (headerParts[0].equals(ResponseCodeEnum.ERROR.toString())) {
            String responsePayload = deserializeResponseObject(payload, String.class, format);
            throw new OperationFailedException(responsePayload);
        } else {
            throw new OperationFailedException("Operation was not completed successfully");
        }
    }

    private static Response readResponse(BufferedReader in) throws IOException, OperationFailedException {
        String[] headerParts;
        String payload;
        String format;
        while (true) {
            headerParts = readHeader(in);
            System.out.println(headerParts[0]);
            payload = readPayload(headerParts, in);
            format = readFormat(headerParts);
            if (headerParts != null && !headerParts[0].isEmpty()) {
                break;
            }
        }
        return new Response(headerParts, payload, format);
    }

    private static String[] readHeader(BufferedReader in) throws IOException {
        String header = in.readLine();
        return header.split("\\|");
    }

    private static String readPayload(String[] headerParts, BufferedReader in) throws IOException {
        String payload = null;
        if(headerParts.length > 1) {
            int payloadLength = Integer.parseInt(headerParts[1]);
            char[] payloadBuffer = new char[payloadLength];
            in.read(payloadBuffer, 0, payloadLength);
            payload = new String(payloadBuffer);
        }
        return payload;
    }

    private static String readFormat(String[] headerParts) throws IOException {
        String format = null;
        if(headerParts.length > 2) {
            format = headerParts[2];
        }
        return format;
    }

    private static ResponseDeserializer getResponseDeserializer(String format) {
        return ResponseDeserializerFactory.createDeserializer(format);
    }

    private static <T> T deserializeResponseObject(String payload, Class<T> responseType, String format) throws OperationFailedException {
        ResponseDeserializer responseDeserializer = getResponseDeserializer(format);
        if (responseType == String.class) {
            return responseDeserializer.deserializeObject(payload, responseType);
        }
        if (responseDeserializer != null) {
            return responseDeserializer.deserializeObject(payload, responseType);
        }
        throw new OperationFailedException("Failed to deserialize object");
    }

    private static <T> List<T> deserializeResponseList(String payload, Class<T> responseType, String format) throws OperationFailedException {
        ResponseDeserializer responseDeserializer = getResponseDeserializer(format);
        if (responseDeserializer != null) {
            return responseDeserializer.deserializeList(payload, responseType);
        }
        throw new OperationFailedException("Failed to deserialize list");
    }
}
