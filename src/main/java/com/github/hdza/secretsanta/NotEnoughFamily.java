package com.github.hdza.secretsanta;

public class NotEnoughFamily extends Exception {
    public NotEnoughFamily() {

    }

    NotEnoughFamily(String message) {
        super (message);
    }

    public NotEnoughFamily(Throwable cause) {
        super (cause);
    }

    public NotEnoughFamily(String message, Throwable cause) {
        super (message, cause);
    }
}

