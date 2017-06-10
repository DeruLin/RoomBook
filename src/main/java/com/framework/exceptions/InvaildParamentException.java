package com.framework.exceptions;

/**
 * Created by deru on 2017/2/6.
 */
public class InvaildParamentException extends RuntimeException{

    public InvaildParamentException() {
    }

    public InvaildParamentException(String message) {
        super(message);
    }

    public InvaildParamentException(Throwable cause) {
        super(cause);
    }

    public InvaildParamentException(String message, Throwable cause) {
        super(message, cause);
    }
}
