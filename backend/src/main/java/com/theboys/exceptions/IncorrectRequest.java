package com.theboys.exceptions;

public class IncorrectRequest extends BasicHttpException {

    public IncorrectRequest(String msg) {
        super(msg);
    }
}
