package org.example.Serializer;

public class ResponseSerializerFactory {
    public static ResponseSerializer createSerializer(String format) {
        switch (format.toUpperCase()) {
            case "JSON":
                return new JsonResponseSerializer();
            default:
                return null;
        }
    }
}
