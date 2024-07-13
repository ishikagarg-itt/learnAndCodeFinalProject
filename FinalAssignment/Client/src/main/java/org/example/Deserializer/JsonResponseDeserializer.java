package org.example.Deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.Exception.OperationFailedException;

import java.lang.reflect.Type;
import java.util.List;

public class JsonResponseDeserializer implements ResponseDeserializer {
    private final Gson gson = new Gson();

    @Override
    public <T> T deserializeObject(String payload, Class<T> responseType) throws OperationFailedException {
        try {
            return gson.fromJson(payload, responseType);
        } catch (JsonSyntaxException e) {
            throw new OperationFailedException("Failed to parse response payload");
        }
    }

    @Override
    public <T> List<T> deserializeList(String payload, Class<T> responseType) throws OperationFailedException {
        Type listType = TypeToken.getParameterized(List.class, responseType).getType();
        try {
            return gson.fromJson(payload, listType);
        } catch (JsonSyntaxException e) {
            throw new OperationFailedException("Failed to parse response payload");
        }
    }
}
