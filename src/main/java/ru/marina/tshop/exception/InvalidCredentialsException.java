package ru.marina.tshop.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(final String errorMessage) {
        super(errorMessage);
    }
}
