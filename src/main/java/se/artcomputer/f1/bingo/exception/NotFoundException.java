package se.artcomputer.f1.bingo.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super("Not Found: " + message);
    }
}
