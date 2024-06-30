package org.example.Dto;

public class Response {
    private final String[] headerParts;
    private final String payload;

    public Response(String[] headerParts, String payload) {
        this.headerParts = headerParts;
        this.payload = payload;
    }

    public String[] getHeaderParts() {
        return headerParts;
    }

    public String getPayload() {
        return payload;
    }
}
