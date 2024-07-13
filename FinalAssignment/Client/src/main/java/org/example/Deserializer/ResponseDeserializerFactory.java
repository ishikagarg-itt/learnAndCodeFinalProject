package org.example.Deserializer;

public class ResponseDeserializerFactory {
    public static ResponseDeserializer createDeserializer(String format) {
        switch (format.toUpperCase()) {
            case "JSON":
                return new JsonResponseDeserializer();
            default:
                return null;
        }
    }
}
