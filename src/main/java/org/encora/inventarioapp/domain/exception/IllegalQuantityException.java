package org.encora.inventarioapp.domain.exception;

public class IllegalQuantityException extends Exception {

    public IllegalQuantityException(String message) {
        super(message);
    }
    public IllegalQuantityException(Throwable cause) {
        super(cause);
    }
}
