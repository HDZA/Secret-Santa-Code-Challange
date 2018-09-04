package com.github.hdza.secretsanta;


/*
    Create a basic custom exception class. It's in the same package since exceptions should be co-located with the things that cause that exception.
 */
public class TooFewForSSException extends Exception {
    public TooFewForSSException() {

    }

    TooFewForSSException(String message) {
        super (message);
    }

    public TooFewForSSException(Throwable cause) {
        super (cause);
    }

    public TooFewForSSException(String message, Throwable cause) {
        super (message, cause);
    }
}
