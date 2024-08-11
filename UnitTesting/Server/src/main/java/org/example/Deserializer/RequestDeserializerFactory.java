package org.example.Deserializer;

public class RequestDeserializerFactory {
    public static RequestDeserializer createDeserializer(String format) {
        switch (format.toUpperCase()) {
            case "JSON":
                return new JsonRequestDeserializer();
            default:
                return null;
        }
    }
}
