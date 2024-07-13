package org.example.Dto;

public class Response {
    private final String[] headerParts;
    private final String payload;

    private final String format;

    public Response(String[] headerParts, String payload, String format) {
        this.headerParts = headerParts;
        this.payload = payload;
        this.format = format;
    }

    public String[] getHeaderParts() {
        return headerParts;
    }

    public String getPayload() {
        return payload;
    }

    public String getFormat() {
        return format;
    }
}
