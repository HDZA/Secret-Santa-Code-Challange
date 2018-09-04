package com.github.hdza.secretsanta;

public class TooFewForPartTwo extends Exception{

    public TooFewForPartTwo() { }

    TooFewForPartTwo(String message) {
        super (message);
    }

    public TooFewForPartTwo(Throwable cause) {
        super (cause);
    }

    public TooFewForPartTwo(String message, Throwable cause) {
        super (message, cause);
    }
}
