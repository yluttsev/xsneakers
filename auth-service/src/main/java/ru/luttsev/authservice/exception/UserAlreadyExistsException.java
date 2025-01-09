package ru.luttsev.authservice.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("User '%s' already exists.".formatted(email));
    }
}
