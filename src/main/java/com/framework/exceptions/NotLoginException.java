package com.framework.exceptions;

/**
 * Created by deru on 2017/2/3.
 */
public class NotLoginException extends RuntimeException {

    public NotLoginException() {
    }

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(Throwable cause) {
        super(cause);
    }

    public NotLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
