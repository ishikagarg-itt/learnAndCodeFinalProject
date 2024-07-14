package org.example.Handler;

import org.example.Constants.FormatEnum;
import org.example.Deserializer.RequestDeserializer;
import org.example.Deserializer.RequestDeserializerFactory;
import org.example.Dto.RequestData;
import org.example.Exception.OperationFailedException;
import org.example.Serializer.ResponseSerializer;
import org.example.Serializer.ResponseSerializerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ProtocolHandler {
    private final PrintWriter out;
    private final BufferedReader in;

    public ProtocolHandler(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    public RequestData receiveRequest(String header) throws IOException, OperationFailedException {
        String[] headerParts = header.split("\\|");
        String messageType = headerParts[0];
        int payloadLength = Integer.parseInt(headerParts[1]);
        FormatEnum format = FormatEnum.fromFormatName(headerParts[2]);
        String sessionToken = headerParts.length > 3 ? headerParts[3] : null;
        String payload = readPayload(in, payloadLength);
        return new RequestData(messageType, payloadLength, payload, format.toString(), sessionToken);
    }

    private String readPayload(BufferedReader in, int payloadLength) throws IOException {
        char[] payloadBuffer = new char[payloadLength];
        in.read(payloadBuffer, 0, payloadLength);
        return new String(payloadBuffer);
    }

    public <T> T deserializeRequestPayload(RequestData requestData, Class<T> requestType) throws OperationFailedException {
        RequestDeserializer deserializer = RequestDeserializerFactory.createDeserializer(requestData.getFormat());
        if (deserializer != null) {
            return deserializer.deserializeObject(requestData.getPayload(), requestType);
        } else {
            throw new OperationFailedException("Unsupported format: " + requestData.getFormat());
        }
    }

    public <T> List<T> deserializeRequestPayloadList(RequestData requestData, Class<T> requestType) throws OperationFailedException {
        RequestDeserializer deserializer = RequestDeserializerFactory.createDeserializer(requestData.getFormat());
        if (deserializer != null) {
            return deserializer.deserializeList(requestData.getPayload(), requestType);
        } else {
            throw new OperationFailedException("Unsupported format: " + requestData.getFormat());
        }
    }

    public void sendResponse(String status, Object responseObject, String format) throws OperationFailedException {
        ResponseSerializer responseSerializer = ResponseSerializerFactory.createSerializer(format);
        String responsePayload = responseSerializer.serialize(responseObject);

        String responseHeader = status + "|" + responsePayload.length() + "|" + format;
        out.println(responseHeader);
        out.println(responsePayload);
        out.flush();
    }

    public void sendError(String errorMessage, String format) throws OperationFailedException {
        sendResponse("ERROR", errorMessage, format);
    }
}
