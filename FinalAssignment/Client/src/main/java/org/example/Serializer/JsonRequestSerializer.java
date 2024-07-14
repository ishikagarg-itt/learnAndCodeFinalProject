package org.example.Serializer;

import com.google.gson.Gson;

public class JsonRequestSerializer implements RequestSerializer {
    private final Gson gson = new Gson();

    @Override
    public String serialize(Object object) {
        return gson.toJson(object);
    }
}
