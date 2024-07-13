package org.example.Dto;

public class RequestData {
    private String messageType;
    private int payloadLength;
    private String payload;
    private String format;
    private String sessionToken;

    public RequestData(String messageType, int payloadLength, String payload, String format, String sessionToken) {
        this.messageType = messageType;
        this.payloadLength = payloadLength;
        this.payload = payload;
        this.format = format;
        this.sessionToken = sessionToken;
    }

    public String getMessageType() {
        return messageType;
    }

    public int getPayloadLength() {
        return payloadLength;
    }

    public String getPayload() {
        return payload;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
