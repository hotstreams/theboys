package com.theboys.exceptions;

public class EntityNotFoundException extends BasicHttpException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
