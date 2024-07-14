package org.example.Dto;

public class RequestHeader {
    private String command;
    private int payloadLength;
    private String format;
    private String sessionToken;
    private String ipAddress;

    public RequestHeader(String command, int payloadLength, String format, String sessionToken, String ipAddress) {
        this.command = command;
        this.payloadLength = payloadLength;
        this.format = format;
        this.sessionToken = sessionToken;
        this.ipAddress = ipAddress;
    }

    public String toHeaderString() {
        if (sessionToken == null) {
            return command + "|" + payloadLength + "|" + format + "|" + ipAddress;
        } else {
            return command + "|" + payloadLength + "|" + format + "|" + sessionToken + "|" + ipAddress;
        }
    }

}
