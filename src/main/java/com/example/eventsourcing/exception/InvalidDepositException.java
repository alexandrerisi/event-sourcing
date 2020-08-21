package com.example.eventsourcing.exception;

public class InvalidDepositException extends RuntimeException {

    public InvalidDepositException(String acc) {
        super("Invalid deposit on -> " + acc);
    }
}
