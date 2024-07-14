package org.example.Handler;

import org.example.Dto.RequestHeader;
import org.example.Exception.OperationFailedException;
import org.example.Serializer.RequestSerializer;
import org.example.Serializer.RequestSerializerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.example.Constants.FormatEnum.JSON;

public class ProtocolHandler {
    private PrintWriter out;
    private BufferedReader in;
    private String ipAddress;

    public ProtocolHandler(PrintWriter out, BufferedReader in, String ipAddress) {
        this.out = out;
        this.in = in;
        this.ipAddress = ipAddress;
    }

    public void sendRequest(String command, Object payload, String sessionToken) throws IOException {
        RequestSerializer requestSerializer = RequestSerializerFactory.createSerializer(JSON.toString());
        String serializedPayload = requestSerializer.serialize(payload);

        RequestHeader header = new RequestHeader(command, serializedPayload.length(), JSON.toString(), sessionToken, ipAddress);
        String headerString = header.toHeaderString();

        System.out.println("header:" + headerString);
        out.println(headerString);
        out.println(serializedPayload);
        out.flush();
    }

    public <T> T receiveResponse(Class<T> responseType) throws IOException, OperationFailedException {
        return ResponseHandler.readResponseObject(in, responseType);
    }

    public <T> List<T> receiveResponseList(Class<T> responseType) throws IOException, OperationFailedException {
        return ResponseHandler.readResponseList(in, responseType);
    }
}
