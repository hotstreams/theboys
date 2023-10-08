package com.theboys.exceptions;

import com.theboys.to.CustomHttpResponse;

public class BasicHttpException extends RuntimeException {

    public BasicHttpException(String message) {
        super(message);
    }

    public CustomHttpResponse getResponse() {
        return new CustomHttpResponse("ERROR", getMessage());
    }
}
