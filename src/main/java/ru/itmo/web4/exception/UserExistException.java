package ru.itmo.web4.exception;

public class UserExistException extends Exception{
    public UserExistException(String message) {
        super(message);
    }
}
