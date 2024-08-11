package org.example.Serializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.Exception.OperationFailedException;

import java.lang.reflect.Type;
import java.util.List;

public class JsonResponseSerializer implements ResponseSerializer {
    private final Gson gson = new Gson();

    @Override
    public String serialize(Object object) {
        return gson.toJson(object);
    }
}
