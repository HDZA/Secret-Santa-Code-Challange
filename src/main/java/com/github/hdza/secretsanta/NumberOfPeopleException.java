package com.github.hdza.secretsanta;


/*
    Create a basic custom exception class. It's in the same package since exceptions should be co-located with the things that cause that exception.
 */
public class NumberOfPeopleException extends Exception {
    public NumberOfPeopleException() {

    }

    NumberOfPeopleException(String message) {
        super (message);
    }

    public NumberOfPeopleException(Throwable cause) {
        super (cause);
    }

    public NumberOfPeopleException(String message, Throwable cause) {
        super (message, cause);
    }
}
