package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {

    Film add(Film film);

    Film update(Film film);

    void delete(Long filmId);


}
