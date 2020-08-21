package com.example.eventsourcing.exception;

public class InvalidWithdrawException extends RuntimeException {

    public InvalidWithdrawException(String acc) {
        super("Invalid Withdraw on -> " + acc);
    }
}
