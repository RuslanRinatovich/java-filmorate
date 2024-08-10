package ru.yandex.practicum.filmorate.exception;

public class RepositoryDbException extends RuntimeException {
    public RepositoryDbException(String message) {
        super(message);
    }
}
