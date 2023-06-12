package com.theboys.exceptions;

import com.theboys.to.BasicHttpException;

public class IncorrectRequest extends BasicHttpException {

    public IncorrectRequest(String msg) {
        super(msg);
    }
}
