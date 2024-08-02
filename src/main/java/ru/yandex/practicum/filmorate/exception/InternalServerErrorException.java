package ru.yandex.practicum.filmorate.exception;

public class InternalServerErrorException extends Throwable {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
