package com.theboys.to;

public class BasicHttpException extends RuntimeException {

    public BasicHttpException(String message) {
        super(message);
    }

    public ErrorResponse getResponse() {
        return new ErrorResponse(getMessage());
    }
}
