package org.example.Serializer;

import org.example.Exception.OperationFailedException;

import java.util.List;

public interface ResponseSerializer {
    String serialize(Object object);
}