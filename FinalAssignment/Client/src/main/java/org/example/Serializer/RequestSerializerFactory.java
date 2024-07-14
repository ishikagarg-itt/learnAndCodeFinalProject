package org.example.Serializer;

public class RequestSerializerFactory {
    public static RequestSerializer createSerializer(String format) {
        switch (format.toUpperCase()) {
            case "JSON":
                return new JsonRequestSerializer();
            default:
                return null;
        }
    }
}
