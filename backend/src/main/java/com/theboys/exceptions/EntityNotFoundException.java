package com.theboys.exceptions;

import com.theboys.to.BasicHttpException;

public class EntityNotFoundException extends BasicHttpException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
