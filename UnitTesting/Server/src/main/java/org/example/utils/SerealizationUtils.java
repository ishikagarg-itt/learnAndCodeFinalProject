package org.example.utils;

import org.example.Deserializer.RequestDeserializerFactory;
import org.example.Deserializer.RequestDeserializer;
import org.example.Serializer.ResponseSerializer;
import org.example.Serializer.ResponseSerializerFactory;

public class SerealizationUtils {
    public static RequestDeserializer getRequestDeserializer(String format) {
        return RequestDeserializerFactory.createDeserializer(format);
    }

    public static ResponseSerializer getResponseSerializer(String format) {
        return ResponseSerializerFactory.createSerializer(format);
    }
}
