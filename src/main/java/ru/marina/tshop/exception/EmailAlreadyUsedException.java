package ru.marina.tshop.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(final String errorMessage) {
        super(errorMessage);
    }
}
