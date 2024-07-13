package org.example.Deserializer;

import org.example.Exception.OperationFailedException;

import java.util.List;

public interface ResponseDeserializer {
    <T> T deserializeObject(String payload, Class<T> responseType) throws OperationFailedException;
    <T> List<T> deserializeList(String payload, Class<T> responseType) throws OperationFailedException;
}