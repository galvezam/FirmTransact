package com.galvez.FirmTransact.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
