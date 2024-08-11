package org.example.Exception;

public class OperationFailedException extends RuntimeException{

    public OperationFailedException(String message){
        super(message);
    }
}
